package robomop;

import static jalse.JALSEBuilder.buildManualJALSE;
import static jalse.actions.MultiActionBuilder.buildChain;
import jalse.JALSE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.Timer;

import robomop.actions.MoveMop;
import robomop.actions.WashFloor;
import robomop.entities.Floor;
import robomop.entities.Mop;

@SuppressWarnings("serial")
public class RoboMopPanel extends JPanel implements ActionListener {

    private static final int TILE_SIZE = 40;

    private final JALSE jalse;

    public RoboMopPanel() {
	// Manually ticked JALSE
	jalse = buildManualJALSE();
	// Create floor and mop
	createEntities();
	// Size to floor size x tile
	setPreferredSize(new Dimension(getFloor().getWidth() * TILE_SIZE, getFloor().getHeight() * TILE_SIZE));
	// Set background
	setBackground(Color.DARK_GRAY);
	// Start ticking and rendering (60 FPS)
	new Timer(1000 / 60, this).start();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	// Tick manual engine
	jalse.resume();

	// Reset floor if clean
	final Floor floor = getFloor();
	if (floor.isClean()) {
	    floor.unwashFloor();
	}

	// Request repaint
	repaint();
    }

    private void createEntities() {
	// Create floor
	final Floor floor = jalse.newEntity(Floor.ID, Floor.class);
	floor.setWidth(10);
	floor.setHeight(10);

	// Create mop
	final Mop mop = floor.newMop();
	mop.setPosition(new Point(0, 0));
	mop.setRandomDirection();

	// Schedule move and wash
	floor.scheduleForActor(buildChain(new MoveMop(), new WashFloor()), 0, 100, TimeUnit.MILLISECONDS);
    }

    private Floor getFloor() {
	return jalse.getEntityAsType(Floor.ID, Floor.class);
    }

    @Override
    protected void paintComponent(final Graphics g) {
	// Draw component as before
	super.paintComponent(g);

	final Floor floor = getFloor();

	// Draw chequered background
	boolean flipCol = true;
	for (int x = 0; x < floor.getWidth(); x++) {
	    for (int y = 0; y < floor.getHeight(); y++) {
		// Set correct next colour
		g.setColor(flipCol ? Color.DARK_GRAY : Color.LIGHT_GRAY);
		flipCol = !flipCol;

		// Draw tile
		g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	    }
	    flipCol = !flipCol;
	}

	// Draw water
	g.setColor(new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 120));
	getFloor().getAllWater().forEach(w -> {
	    final Point waterPos = w.getPosition();
	    g.fillRect(waterPos.x * TILE_SIZE, waterPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	});

	// Draw mop
	g.setColor(Color.RED.darker());
	final Point mopPos = getFloor().getMop().getPosition();
	g.fillOval(mopPos.x * TILE_SIZE, mopPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

	// Sync (Linux fix)
	Toolkit.getDefaultToolkit().sync();
    }
}
