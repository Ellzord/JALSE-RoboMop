package robomop.entities;

import java.awt.Point;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

public interface Water extends Entity {

    @GetAttribute
    Point getPosition();

    @SetAttribute
    void setPosition(Point pos);
}
