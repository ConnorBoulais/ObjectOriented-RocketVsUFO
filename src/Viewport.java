final class Viewport
{
   private int row;
   private int col;
   private int numRows;
   private int numCols;

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }

   public int getRow() {return this.row;}
   public int getCol() {return this.col;}
   public int getNumRows() {return this.numRows;}
   public int getNumCols() {return this.numCols;}

   private void setRow(int rowVal) {this.row = rowVal;}
   private void setCol(int colVal) {this.col = colVal;}

   public void shift(int col, int row)
   {
      this.setCol(col);
      this.setRow(row);
   }

   public boolean contains(Point p)
   {
      return p.getY() >= this.getRow() && p.getY() < this.getRow() + this.getNumRows() &&
              p.getX() >= this.getCol() && p.getX() < this.getCol() + this.getNumCols();
   }

   public Point viewportToWorld(int col, int row)
   {
      return new Point(col + this.getCol(), row + this.getRow());
   }

   public Point worldToViewport(int col, int row)
   {
      return new Point(col - this.getCol(), row - this.getRow());
   }
}
