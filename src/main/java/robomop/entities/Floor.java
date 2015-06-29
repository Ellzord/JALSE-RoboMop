package robomop.entities;

import java.util.Set;
import java.util.UUID;

import jalse.entities.Entity;
import jalse.entities.annotations.EntityID;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.GetEntities;
import jalse.entities.annotations.GetEntity;
import jalse.entities.annotations.NewEntity;
import jalse.entities.annotations.SetAttribute;

public interface Floor extends Entity {

    UUID ID = new UUID(0, 0);

    @GetEntities
    Set<Water> getAllWater();

    @GetAttribute
    int getHeight();

    @EntityID(mostSigBits = 0, leastSigBits = 1)
    @GetEntity
    Mop getMop();

    @GetAttribute
    int getWidth();

    default boolean isClean() {
	final int cleanTiles = getEntitiesOfType(Water.class).size();
	final int totalTiles = getWidth() * getHeight();
	return cleanTiles >= totalTiles;
    }

    @EntityID(mostSigBits = 0, leastSigBits = 1)
    @NewEntity
    Mop newMop();

    @NewEntity
    Water newWater();

    @SetAttribute
    void setHeight(int height);

    @SetAttribute
    void setWidth(int width);

    default void unwashFloor() {
	getEntitiesOfType(Water.class).forEach(Entity::kill);
    }
}
