import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

final class WorldView
{
   private PApplet screen;
   private WorldModel world;
   private int tileWidth;
   private int tileHeight;
   private Viewport viewport;

   private PApplet getScreen() {return this.screen;}
   private WorldModel getWorld() {return this.world;}
   private int getTileWidth() {return this.tileWidth;}
   private int getTileHeight() {return this.tileHeight;}
   public Viewport getViewport() {return this.viewport;}

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }

   public void shiftView(int colDelta, int rowDelta)
   {
      int newCol = ImageLoad.clamp(this.viewport.getCol() + colDelta, 0,
              this.world.getNumCols() - this.viewport.getNumCols());
      int newRow = ImageLoad.clamp(this.viewport.getRow() + rowDelta, 0,
              this.world.getNumRows() - this.viewport.getNumRows());

      this.viewport.shift(newCol, newRow);
   }

   private void drawBackground()
   {
      for (int row = 0; row < this.getViewport().getNumRows(); row++)
      {
         for (int col = 0; col < this.getViewport().getNumCols(); col++)
         {
            Point worldPoint = this.getViewport().viewportToWorld(col, row);
            Optional<PImage> image = this.getWorld().getBackgroundImage(worldPoint);
            if (image.isPresent())
            {
               this.getScreen().image(image.get(), col * this.getTileWidth(),
                       row * this.getTileHeight());
            }
         }
      }
   }

   private void drawEntities()
   {
      for (Entity entity : this.getWorld().getEntities())
      {
         Point pos = entity.getPosition();

         if (this.getViewport().contains(pos))
         {
            Point viewPoint = this.getViewport().worldToViewport(pos.getX(), pos.getY());
            this.getScreen().image(entity.getCurrentImage(),
                    viewPoint.getX() * this.getTileWidth(), viewPoint.getY() * this.getTileHeight());
         }
      }
   }

   public void drawViewport()
   {
      this.drawBackground();
      this.drawEntities();
   }


}
