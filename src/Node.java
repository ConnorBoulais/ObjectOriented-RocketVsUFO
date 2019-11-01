import java.util.Objects;

public class Node implements Comparable{

    private Point loc;
    private int g;
    private int h;
    private Node prior;

    public Node(Point loc, int g, int h, Node prior){
        this.loc = loc;
        this.g = g;
        this.h = h;
        this.prior = prior;
    }

    public Node(Point loc){
        this.loc = loc;
        g = 0;
        h = 0;
        prior = null;
    }

    public int getG() {return g;}
    public int getH() {return h;}
    public int getF() {return g + h;}
    public Node getPrior() {return prior;}
    public Point getPoint() {return loc;}

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setPrior(Node prior) {
        this.prior = prior;
    }

    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if(getClass() != other.getClass()){
            return false;
        }
        Node node = (Node)other;
        return this.getPoint().equals(node.getPoint());
    }

    public String toString(){
        return "Node at :" + this.loc.toString();
    }

    public int compareTo(Object other){
        Node n2 = (Node)other;

        if(this.getF() < n2.getF()){
            return -1;
        }
        if(this.getF() > n2.getF()){
            return 1;
        }
        if(this.getG() > n2.getG()){
            return -1;
        }
        if(this.getG() < n2.getG()){
            return 1;
        }
        return 0;
    }
}
