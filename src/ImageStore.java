import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

final class ImageStore
{
   private Map<String, List<PImage>> images;
   private List<PImage> defaultImages;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }

   public Map<String, List<PImage>> getImages() {return this.images;}
   private List<PImage> getDefaultImages() {return this.defaultImages;}

   public List<PImage> getImageList(String key)
   {
      return this.getImages().getOrDefault(key, this.getDefaultImages());
   }
}
