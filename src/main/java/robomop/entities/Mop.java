package robomop.entities;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import robomop.attributes.Direction;

public interface Mop extends Entity {

    @GetAttribute
    Direction getDirection();

    @GetAttribute
    Point getPosition();

    @SetAttribute
    void setDirection(Direction dir);

    @SetAttribute
    void setPosition(Point pos);

    default void setRandomDirection() {
	// Current direction
	final Direction currentDir = getDirection();

	// Get different random direction
	final Direction[] dirs = Direction.values();
	Direction newDir;
	do {
	    newDir = dirs[ThreadLocalRandom.current().nextInt(dirs.length)];
	} while (newDir == currentDir);

	// Set new direction
	setDirection(newDir);
    }
}
