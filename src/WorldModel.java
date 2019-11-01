import processing.core.PImage;

import java.util.*;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   public int getNumRows() {return this.numRows;}
   public int getNumCols() {return this.numCols;}
   private Background getBackground(int y, int x) {return this.background[y] [x];}
   private Entity getOccupancy(int y, int x) {return this.occupancy[y][x];}
   public Set<Entity> getEntities() {return this.entities;}
   private void setBackground(int y, int x, Background background)
   {
      this.background[y][x] = background;
   }
   private void setOccupancy(int y, int x, Entity entity)
   {
      this.occupancy[y][x] = entity;
   }

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < this.getNumRows() &&
              pos.getX() >= 0 && pos.getX() < this.getNumCols();
   }

   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public void addEntity(Entity entity)
   {
      if (this.withinBounds(entity.getPosition()))
      {
         this.setOccupancyCell(entity.getPosition(), entity);
         this.getEntities().add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.getEntities().remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public void removeEntity(Entity entity)
   {
      this.removeEntityAt(entity.getPosition());
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }

   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos)
   {
      return this.getOccupancy(pos.getY(),pos.getX());
   }

   private void setOccupancyCell(Point pos, Entity entity)
   {
      this.setOccupancy(pos.getY(), pos.getX(), entity);
   }

   private Background getBackgroundCell(Point pos)
   {
      return this.getBackground(pos.getY(), pos.getX());
   }

   private void setBackgroundCell(Point pos, Background background)
   {
      this.setBackground(pos.getY(), pos.getX(),background);
   }

   public Optional<Entity> getRocket() {
      for (Entity entity : this.getEntities()) {
         if (entity instanceof Rocket) {
            return Optional.of(entity);
         }
      }
      return Optional.empty();
   }

   public Optional<Entity> getStar() {
      for (Entity entity : this.getEntities()) {
         if (entity instanceof Star) {
            return Optional.of(entity);
         }
      }
      return Optional.empty();
   }

   public boolean isGameGoing(){
      for (Entity entity : this.getEntities()) {
         if (entity instanceof Rocket) {
            return true;
         }
      }
      return false;
   }

   public Point wrapAroundBounds(Point destPos){

      if(destPos.getY() == this.getNumRows()){
         destPos = new Point(destPos.getX(), 0);
      }
      else if(destPos.getY() == -1){
         destPos = new Point(destPos.getX(), this.getNumRows()-1);
      }
      else if(destPos.getX() == this.getNumCols()){
         destPos = new Point(0, destPos.getY());
      }
      else if(destPos.getX() == -1){
         destPos = new Point(this.getNumCols()-1, destPos.getY());
      }

      return destPos;
   }
}
