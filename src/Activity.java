public class Activity implements Action{

    private EntityActivity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(WorldModel world, ImageStore imageStore, EntityActivity entity)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.executeActivity(this.world, this.imageStore, scheduler);
    }
}
