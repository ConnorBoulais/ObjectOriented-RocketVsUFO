import processing.core.PImage;

import java.util.List;

public abstract class EntityActivity extends Entity
{
    private int actionPeriod;

    protected EntityActivity(String id, Point position, int actionPeriod, List<PImage> images){
        super(id, position, images, 0);
        this.actionPeriod = actionPeriod;
    }

    protected int getActionPeriod() {return this.actionPeriod;}

    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity(world, imageStore, this),
                this.getActionPeriod());
    }
    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
