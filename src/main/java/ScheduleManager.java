import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleManager {

    private static Gson GSON = new Gson();

    public HashMap<String, ScheduleComposite> getScheduleCompositeByUserId() {
        return scheduleCompositeByUserId;
    }

    private HashMap<String, ScheduleComposite> scheduleCompositeByUserId = new HashMap<String, ScheduleComposite>();

    public boolean init() {
        boolean loaded = Bot.getDatabase().loadAll(this);
        //debugList();
        return loaded;
    }

    private void debugList() {
        for (Map.Entry<String, ScheduleComposite> e1 : scheduleCompositeByUserId.entrySet()) {
            System.out.println("User: " + e1.getKey());
            for (ScheduleComposite.Schedule schedule : e1.getValue().getSchedules()) {
                System.out.println("\t" + schedule.toString());
            }
        }
    }

    /**
     * Gets all schedules related to user
     * @param userId
     */
    @NotNull
    public ScheduleComposite getScheduleComposite(String userId) {
        scheduleCompositeByUserId.putIfAbsent(userId, new ScheduleComposite(userId));
        return scheduleCompositeByUserId.get(userId);
    }


    public class ScheduleComposite {
        private String userId;
        private HashMap<String, Schedule> scheduleByName = new HashMap<String, Schedule>();

        public ScheduleComposite(String userId){
            this.userId = userId;
        }

        /**
         * Get schedule by name
         * Creates one if doesn't exist
         * @param scheduleName name of the schedule
         * @return schedule
         */
        @NotNull
        public Schedule createOrGetSchedule(String scheduleName){
            if (!scheduleByName.containsKey(scheduleName)){
                scheduleByName.put(scheduleName, new Schedule(scheduleName, userId));
                Bot.getDatabase().createSchedule(userId, scheduleName);
            }

            return scheduleByName.get(scheduleName);
        }

        @Nullable
        public Schedule getSchedule(String scheduleName){
            return scheduleByName.get(scheduleName);
        }

        /**
         * Deletes schedule
         * @param scheduleName name of the schedule
         * @return error code or null if none
         */
        public String deleteSchedule(String scheduleName) {
            if (this.getSchedule(scheduleName) == null) {
                return "schedule doesn't exist";
            }

            if (Bot.getDatabase().deleteSchedule(userId, scheduleName))
                return null;
            else
                return "database error";
        }

        public void loadSchedule(String scheduleName, String eventsJson){
            scheduleByName.put(scheduleName, new Schedule(scheduleName, userId));
            scheduleByName.get(scheduleName).deserialiseEvents(eventsJson);
        }

        public Collection<Schedule> getSchedules() {
            return this.scheduleByName.values();
        }

        public class Schedule {
            private String scheduleName;
            private String userId;

            public Collection<ScheduleEvent> getEvents() {
                return eventByName.values();
            }

            private HashMap<String, ScheduleEvent> eventByName = new HashMap<String, ScheduleEvent>();

            public Schedule(String scheduleName, String userId){
                this.scheduleName = scheduleName;
                this.userId = userId;
            }

            /**
             * Updates schedule in database
             * Creates it if doesnt exist
             */
            public boolean updateDatabase() {
                Bot.getDatabase().updateSchedule(this);
                return true;
            }

            /**
             * Creates or updates event
             * @param eventName
             * @param day
             * @param time
             * @param duration
             * @param notificationOffset
             * @param description
             */
            public void updateEvent(String eventName, String day, String time, Integer duration, Integer notificationOffset, String description) {
                eventByName.putIfAbsent(eventName, new ScheduleEvent(eventName));
                eventByName.get(eventName).update(day, time, duration, notificationOffset, description);
                this.updateDatabase();
            }

            @Nullable
            public ScheduleEvent getEvent(String eventName){
                return eventByName.get(eventName);
            }

            /**
             * Deletes event
             * @param eventName
             * @return error code or null if none
             */
            public String deleteEvent(String eventName) {

                if (this.getEvent(eventName) == null) {
                    return "event doesn't exist";
                }

                this.eventByName.remove(eventName);
                this.updateDatabase();

                return null;
            }

            public void deserialiseEvents(String eventsJson){
                this.eventByName = GSON.fromJson(eventsJson, new TypeToken<HashMap<String, ScheduleEvent>>(){}.getType());
            }


            public String serialiseEvents() {
                return GSON.toJson(eventByName);
            }

            public String toString(){
                return "{ scheduleName: " + scheduleName
                        + " | userId: " + userId
                        +  " | events: " + eventByName.values().toString()
                        + "}";
            }

            public String getScheduleName() {
                return scheduleName;
            }

            public String getUserId() {
                return userId;
            }

            public class ScheduleEvent {
                private String eventName;
                private String day;
                private String time;
                private Integer duration;
                private Integer notificationOffset;
                private String description;

                public ScheduleEvent(String eventName){
                    this.eventName = eventName;
                }

                public void update(String day, String time, Integer duration, Integer notificationOffset, String description) {
                    this.day = day;
                    this.time = time;
                    this.duration = duration;
                    this.notificationOffset = notificationOffset;
                    this.description = description;
                }

                public String toString(){
                    return "[eventName: " + eventName
                            + " | day: " + day
                            + " | time: " + time
                            + " | duration: " + duration
                            + " | notificationOffset: " + notificationOffset
                            + " | description: " + description
                            + "]";
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public Integer getDuration() {
                    return duration;
                }

                public void setDuration(Integer duration) {
                    this.duration = duration;
                }

                public Integer getNotificationOffset() {
                    return notificationOffset;
                }

                public void setNotificationOffset(Integer notificationOffset) {
                    this.notificationOffset = notificationOffset;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }

        }

    }

}
