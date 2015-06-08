package robomop.actions;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;

import java.awt.Point;

import robomop.entities.Floor;
import robomop.entities.Water;

public class WashFloor implements Action<Entity> {

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	// Get floor
	final Floor floor = context.getActor().asType(Floor.class);

	// Check if water at mops position
	final Point mopPos = floor.getMop().getPosition();
	if (!floor.streamEntitiesOfType(Water.class).anyMatch(w -> mopPos.equals(w.getPosition()))) {
	    // Create new water
	    final Water water = floor.newWater();
	    water.setPosition(mopPos);
	}
    }
}
