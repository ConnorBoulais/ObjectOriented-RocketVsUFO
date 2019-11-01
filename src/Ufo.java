import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Ufo extends EntityActivity{

    public Ufo(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id, position, actionPeriod, images);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = this.getPosition().findNearest(world, Rocket.class);

        if (!notFullTarget.isPresent() ||
                !this.moveTo(world, notFullTarget.get(), scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(world, imageStore, this),
                    this.getActionPeriod());
        }
    }

    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            Entity quake = new Quake(target.getPosition(), imageStore.getImageList("quake"));
            world.removeEntity(target);
            world.removeEntity(world.getStar().get());
            world.addEntity(quake);
            ((Quake) quake).scheduleActions(scheduler, world, imageStore);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    protected Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> path;

        Point start = new Point(this.getPosition().getX(), this.getPosition().getY());
        Point end = new Point(destPos.getX(), destPos.getY());
        Predicate<Point> canPassThrough = (p) -> (!world.isOccupied(p)) && world.withinBounds(p);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.adjacent(p2);


        AStarPathingStrategy Astar = new AStarPathingStrategy();
        path = Astar.computePath(
                start,
                end,
                canPassThrough,
                withinReach,
                PathingStrategy.ALL_NEIGHBORS);

        if(path.isEmpty()){
            return this.getPosition();
        }
        else {
            return path.get(0);
        }
    }

}
