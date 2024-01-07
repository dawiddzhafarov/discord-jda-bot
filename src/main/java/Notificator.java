import net.dv8tion.jda.api.entities.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Notificator {

    public void start() {
        Timer timer = new Timer();

        long delay = 0;
        long period = 60000;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Wywołano metodę co określony czas");
                notificate();
            }
        };

        timer.scheduleAtFixedRate(task, delay, period);
    }

    private void notificate(){

        Bot.getScheduleManager().getScheduleCompositeByUserId().entrySet().stream()
                .forEach(schedulesPerUser ->
                        schedulesPerUser.getValue().getSchedules()
                                .forEach(schedule ->
                                        schedule.getEvents()
                                                .forEach(event -> tryToNotify(event, schedulesPerUser.getKey()))))
        ;
    }

    private void tryToNotify(ScheduleManager.ScheduleComposite.Schedule.ScheduleEvent event, String userId){
        if (event.getNotificationOffset() == null) {
            return;
        }
        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        if (!currentDayOfWeek.equals(DayOfWeek.valueOf(event.getDay())))
            return;


        int offset = event.getNotificationOffset();
        int offsetHours = offset/60;
        int offsetMinutes = offset%60;

        int eventHour = Integer.parseInt(event.getTime().split(":")[0]);
        int eventMinutes = Integer.parseInt(event.getTime().split(":")[1]);

        LocalTime currentTime = LocalTime.now();
        if (eventHour - offsetHours != currentTime.getHour())
            return;

        if (eventMinutes - offsetMinutes != currentTime.getMinute())
            return;

        User user = Bot.jda.retrieveUserById(userId).complete();

        user.openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessage("Your " + event + " is due soon").queue();
        });
    }
}
