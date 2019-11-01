import processing.core.PImage;

import java.util.List;

public class Quake extends EntityAnimation{

    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final String QUAKE_KEY = "quake";

    public Quake(Point position, List<PImage> images)
    {
        super(QUAKE_ID, position, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD, images);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}
