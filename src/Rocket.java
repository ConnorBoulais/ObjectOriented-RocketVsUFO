import org.omg.CORBA.IMP_LIMIT;
import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Rocket extends Entity {

    public Rocket(String id, Point position, List<PImage> images)
    {
        super(id, position, images, 0);
    }

    public void moveRocket(int dx, int dy, EventScheduler scheduler, WorldModel world, ImageStore imageStore){

        Point destPos = new Point(this.getPosition().getX() + dx, this.getPosition().getY() + dy);

        destPos = world.wrapAroundBounds(destPos);

        Optional<Entity> star = world.getStar();

        if(star.isPresent() && star.get().getPosition().equals(destPos)){

            ((Star)star.get()).moveStar(world, imageStore, scheduler);
            world.moveEntity(this, destPos);
        }
        else if(world.getOccupancyCell(destPos) == null) {
            world.moveEntity(this, destPos);
        }
    }


    public static void startGame(Point point, WorldModel world,ImageStore imageStore, EventScheduler scheduler){
        Optional<Entity> starOp = world.getStar();
        if(starOp.isPresent()){
            world.removeEntity(starOp.get());
        }

        if(!world.isOccupied(point)){
            Star.startStar(scheduler, world, imageStore);
            startRocket(point, world, imageStore, scheduler);
        }
    }

    public static void startRocket(Point point, WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Entity rocket = new Rocket("rocket", new Point(10,10),
                imageStore.getImageList("rocket"));
        rocket.setPosition(point);
        world.addEntity(rocket);
    }
}
