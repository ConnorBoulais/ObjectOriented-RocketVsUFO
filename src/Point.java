import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

final class Point
{
   private final int x;
   private final int y;

   private static final int ORE_REACH = 1;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public int getX() {return this.x;}
   public int getY() {return this.y;}

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent(Point p2)
   {
      return (this.getX() == p2.getX() && Math.abs(this.getY() - p2.getY()) == 1) ||
              (this.getY() == p2.getY() && Math.abs(this.getX() - p2.getX()) == 1);
   }

   public Optional<Point> findOpenAround(WorldModel world)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(this.getX() + dx, this.getY() + dy);
            if (world.withinBounds(newPt) &&
                    !world.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }
      return Optional.empty();
   }

   private Optional<Entity> nearestEntity(List<Entity> entities)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(this);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(this);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }
         return Optional.of(nearest);
      }
   }

   private int distanceSquared(Point p2)
   {
      int deltaX = this.getX() - p2.getX();
      int deltaY = this.getY() - p2.getY();

      return deltaX * deltaX + deltaY * deltaY;
   }

   public Optional<Entity> findNearest(WorldModel world, Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : world.getEntities())
      {
         if (kind.isInstance(entity))
         {
            ofType.add(entity);
         }
      }

      return this.nearestEntity(ofType);
   }
}
