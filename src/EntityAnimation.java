import processing.core.PImage;

import java.util.List;

public abstract class EntityAnimation extends EntityActivity {
    private int animationPeriod;
    public final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    protected EntityAnimation(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, images);
        this.animationPeriod = animationPeriod;
    }

    protected int getAnimationPeriod() {
        return this.animationPeriod;
    }

    protected void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }

    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity(world, imageStore, this),
                this.getAnimationPeriod());
        if(this instanceof Quake){
            scheduler.scheduleEvent(this,
                    new Animation(QUAKE_ANIMATION_REPEAT_COUNT, this),
                    this.getAnimationPeriod());
        }
        else{
            scheduler.scheduleEvent(this, new Animation(0, this),
                    this.getAnimationPeriod());
        }
    }
}
