import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WorldLoad {

    private static final int PROPERTY_KEY = 0;

    private static final String BGND_KEY = "background";
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    private static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    public static final String ORE_KEY = "ore";
    private static final String SMITH_KEY = "blacksmith";
    private static final String VEIN_KEY = "vein";

    private static boolean parseBackground(WorldModel world, String [] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == BGND_NUM_PROPERTIES;
    }

    private static boolean parseMiner(WorldModel world, String [] properties, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Entity entity = new Ufo(properties[MINER_ID],
                    //Integer.parseInt(properties[MINER_LIMIT]),
                    pt,
                    /*Integer.parseInt(properties[MINER_ACTION_PERIOD]*/ 2300,
                    ///*Integer.parseInt(properties[MINER_ANIMATION_PERIOD]*/ 0,
                    imageStore.getImageList("ufo"));
            world.tryAddEntity(entity);
        }
        return properties.length == MINER_NUM_PROPERTIES;
    }

    private static boolean parseObstacle(WorldModel world, String [] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[OBSTACLE_ID],
                    pt,
                    imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }
        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    private static boolean processLine(WorldModel world, String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case BGND_KEY:
                    return parseBackground(world, properties, imageStore);
                case MINER_KEY:
                    return parseMiner(world, properties, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(world, properties, imageStore);
                case ORE_KEY:
                    return true;
                case SMITH_KEY:
                    return true;
                case VEIN_KEY:
                    return true;
            }
        }
        return false;
    }

    private static void load(WorldModel world, Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!processLine(world, in.nextLine(), imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }

    public static void loadWorld(WorldModel world, String filename,
                                  ImageStore imageStore)
    {
        try
        {
            Scanner in = new Scanner(new File(filename));
            WorldLoad.load(world, in, imageStore);
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
