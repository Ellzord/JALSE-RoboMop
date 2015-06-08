package robomop.actions;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import robomop.entities.Floor;
import robomop.entities.Mop;

public class MoveMop implements Action<Entity> {

    private static Point getPositionInDirection(final Mop mop) {
	// Current position
	final Point pos = mop.getPosition();

	// Move position in direction
	final Point newPos = new Point();
	switch (mop.getDirection()) {
	case NORTH:
	    newPos.setLocation(pos.x, pos.y - 1);
	    break;
	case NORTH_EAST:
	    newPos.setLocation(pos.x + 1, pos.y - 1);
	    break;
	case EAST:
	    newPos.setLocation(pos.x + 1, pos.y);
	    break;
	case SOUTH_EAST:
	    newPos.setLocation(pos.x + 1, pos.y + 1);
	    break;
	case SOUTH:
	    newPos.setLocation(pos.x, pos.y + 1);
	    break;
	case SOUTH_WEST:
	    newPos.setLocation(pos.x - 1, pos.y + 1);
	    break;
	case WEST:
	    newPos.setLocation(pos.x - 1, pos.y);
	    break;
	case NORTH_WEST:
	    newPos.setLocation(pos.x - 1, pos.y - 1);
	    break;
	}
	return newPos;
    }

    private static boolean inFloorBounds(final Floor floor, final Point pos) {
	return pos.x >= 0 && pos.x < floor.getWidth() && pos.y >= 0 && pos.y < floor.getHeight();
    }

    private static boolean isAtFloorEdge(final Floor floor, final Point pos) {
	return pos.x == 0 || pos.x == floor.getWidth() - 1 || pos.y == 0 || pos.y == floor.getHeight() - 1;
    }

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	// Get floor
	final Floor floor = context.getActor().asType(Floor.class);

	// Get mop
	final Mop mop = floor.getMop();

	for (;;) {
	    final Point movedPos = getPositionInDirection(mop);
	    // Check in bounds
	    if (inFloorBounds(floor, movedPos)) {
		// Randomly ping off when at an edge
		if (isAtFloorEdge(floor, movedPos) && ThreadLocalRandom.current().nextBoolean()) {
		    mop.setRandomDirection();
		}

		// Move mop
		mop.setPosition(movedPos);
		break;
	    }
	    // Change direction
	    mop.setRandomDirection();
	}
    }
}
