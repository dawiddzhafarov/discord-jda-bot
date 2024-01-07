import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class NotificationCommand extends ListenerAdapter  {

    public void init() {

        Bot.jda.upsertCommand("update-notification", "Updates the notification for event")
                .addOption(OptionType.STRING, "schedule-name", "Name of the schedule")
                .addOption(OptionType.STRING, "event-name", "Name of the event")
                .addOption(OptionType.INTEGER, "notification-offset", "Offset for notification")
                .queue();
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("update-notification")) {

            String scheduleName;
            String eventName;
            try {
                scheduleName = event.getOption("schedule-name").getAsString();
                eventName = event.getOption("event-name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name and Event Name").setEphemeral(true).queue();
                return;
            }

            ScheduleManager.ScheduleComposite.Schedule schedule = Bot.getScheduleManager().getScheduleComposite(event.getUser().getId())
                    .getSchedule(scheduleName);
            if (schedule == null){
                event.reply("Schedule with name: "+scheduleName+" does not exist!").setEphemeral(true).queue();
                return;
            }

            ScheduleManager.ScheduleComposite.Schedule.ScheduleEvent scheduleEvent = schedule.getEvent(eventName);
            if (scheduleEvent == null) {
                event.reply("Schedule event with name: "+eventName+" does not exist!").setEphemeral(true).queue();
                return;
            }

            Integer notificationOffset = event.getOption("notification-offset", null, OptionMapping::getAsInt);

            schedule.updateEvent(eventName, scheduleEvent.getDay(), scheduleEvent.getTime(), scheduleEvent.getDuration(), notificationOffset, scheduleEvent.getDescription());

            event.reply("Schedule *" + scheduleName + "* event *" + eventName + "* updated!").setEphemeral(true).queue();
        }

    }
}
