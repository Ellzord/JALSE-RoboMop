package robomop.entities;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

import java.awt.Point;

public interface Water extends Entity {

    @GetAttribute
    Point getPosition();

    @SetAttribute
    void setPosition(Point pos);
}
