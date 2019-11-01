import java.util.*;

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }

   private PriorityQueue<Event> getEventQueue() {return this.eventQueue;}
   private Map<Entity, List<Event>> getPendingEvents() {return this.pendingEvents;}
   private double getTimeScale() {return this.timeScale;}

   public void scheduleEvent(Entity entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * this.getTimeScale());
      Event event = new Event(action, time, entity);

      this.getEventQueue().add(event);

      // update list of pending events for the given entity
      List<Event> pending = this.getPendingEvents().getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      this.getPendingEvents().put(entity, pending);
   }

   public void unscheduleAllEvents(Entity entity)
   {
      List<Event> pending = this.getPendingEvents().remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            this.getEventQueue().remove(event);
         }
      }
   }

   private void removePendingEvent(Event event)
   {
      List<Event> pending = this.getPendingEvents().get(event.getEntity());

      if (pending != null)
      {
         pending.remove(event);
      }
   }

   public void updateOnTime(long time)
   {
      while (!this.getEventQueue().isEmpty() &&
              this.getEventQueue().peek().getTime() < time)
      {
         Event next = this.getEventQueue().poll();

         this.removePendingEvent(next);

         next.getAction().executeAction(this);
      }
   }
}
