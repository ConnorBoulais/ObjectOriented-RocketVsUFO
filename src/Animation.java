public class Animation implements Action{

    private EntityAnimation entity;
    private int repeatCount;

    public Animation(int repeatCount, EntityAnimation entity)
    {
        this.repeatCount = repeatCount;
        this.entity = entity;
    }

    public void executeAction(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    new Animation(Math.max(this.repeatCount - 1, 0), this.entity),
                    this.entity.getAnimationPeriod());
        }
    }
}
