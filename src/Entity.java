import processing.core.PImage;

import java.util.List;

public abstract class Entity
{
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;

   protected Entity(String id, Point position, List<PImage> images, int imageIndex){
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
   }

   protected Point getPosition() {return this.position;}
   protected void setPosition(Point pos) {this.position = pos;}
   protected PImage getCurrentImage(){
      return this.images.get(this.imageIndex);
   }
   protected String getId() {return this.id;}
   protected int getImageIndex() { return this.imageIndex;}
   protected List<PImage> getImages() {return this.images;}
   protected void setImageIndex(int index){
      this.imageIndex = index;
   }
}
