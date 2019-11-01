import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {

        Node currentNode = new Node(start);
        currentNode.setG(0);
        currentNode.setH(Math.abs(currentNode.getPoint().getX() - end.getX()) + Math.abs(currentNode.getPoint().getY() - end.getY()));

        PriorityQueue<Node> openList = new PriorityQueue<>();
        List<Point> closedList = new LinkedList<>();
        openList.add(currentNode);

        while(!openList.isEmpty()){

            currentNode = openList.poll();

            if(currentNode.getPoint().adjacent(end)){
                break;
            }

            List<Point> neighbors = potentialNeighbors.apply(currentNode.getPoint())
                    .filter(canPassThrough)
                    .filter(p -> !closedList.contains(p))
                    .collect(Collectors.toList());

            addToOpenListIfNotThere(openList, neighbors, end, currentNode);

            if(!currentNode.getPoint().equals(start)){
                closedList.add(currentNode.getPoint());
            }
        }

        closedList.add(currentNode.getPoint());
        return closedList;
    }

    private static void addToOpenListIfNotThere(Queue<Node> openList, List<Point> neighbors,
                                                Point end, Node currentNode){
        for(Point point : neighbors){

            Node node = new Node(point);
            if(!openList.contains(node)){

                //SET G
                if(node.getPrior() != null){
                    node.setG(node.getPrior().getG() + 1);
                }
                else {
                    node.setG(1);
                }

                //SET H
                node.setH(Math.abs(node.getPoint().getX() - end.getX()) + Math.abs(node.getPoint().getY() - end.getY()));

                //SET PRIOR
                node.setPrior(currentNode);

                openList.add(node);

            }
        }
    }
}
