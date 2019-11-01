import processing.core.PImage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Star extends Entity{

    private static final Random rand = new Random();
    private final List<Point> starPoints =
            Arrays.asList(new Point(12,18),
                    new Point(26, 14),
                    new Point(20, 2),
                    new Point(20, 28),
                    new Point(3, 14),
                    new Point(30, 14),
                    new Point(26, 25));

    public Star(String id, Point position, List<PImage> images)
    {
        super(id, position, images, 0);
    }

    public void moveStar(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        int idx = rand.nextInt(7);
        Point pos = starPoints.get(idx % 7);
        while (world.isOccupied(pos)){
            idx = rand.nextInt(7);
            pos = starPoints.get(idx % 7);
        }
        world.moveEntity(this, pos);
    }

    public static void startStar(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        Entity star = new Star("star", new Point(16,20), imageStore.getImageList("star"));
        world.addEntity(star);
        ((Star)star).moveStar(world, imageStore, scheduler);
    }
}
