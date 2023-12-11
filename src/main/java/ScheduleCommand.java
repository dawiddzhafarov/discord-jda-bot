import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.List;

public class ScheduleCommand extends ListenerAdapter {

    public void init(){
        Bot.jda.upsertCommand("set-schedule", "Creates the schedule with given event")
                .addOption(OptionType.STRING, "schedule-name", "Name of the schedule")
                .addOption(OptionType.STRING, "event-name", "Name of the event")
                .addOption(OptionType.STRING, "day-of-the-week", "Day of the week")
                .addOption(OptionType.STRING, "time", "Time of the event")
                .addOption(OptionType.INTEGER, "duration", "Duration of the event")
                .addOption(OptionType.STRING, "description", "Description of the event")
                .queue();

        Bot.jda.upsertCommand("update-schedule", "Updates the schedule")
                .addOption(OptionType.STRING, "schedule-name", "Name of the schedule")
                .addOption(OptionType.STRING, "event-name", "Name of the event")
                .addOption(OptionType.STRING, "day-of-the-week", "Day of the week")
                .addOption(OptionType.STRING, "time", "Time of the event")
                .addOption(OptionType.INTEGER, "duration", "Duration of the event")
                .addOption(OptionType.STRING, "description", "Description of the event")
                .queue();

        Bot.jda.upsertCommand("delete-schedule", "Deletes the schedule")
                .addOption(OptionType.STRING, "schedule-name", "Name of the schedule")
                .queue();

        Bot.jda.upsertCommand("delete-schedule-event", "Deletes the schedule")
                .addOption(OptionType.STRING, "schedule-name", "Name of the schedule")
                .addOption(OptionType.STRING, "event-name", "Name of the event")
                .queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-schedule")) {
            String scheduleName = null;
            String eventName = null;
            String day = null;
            String time = null;
            Integer duration = null;
            try {
                scheduleName = event.getOption("schedule-name").getAsString();
                eventName = event.getOption("event-name").getAsString();
                day = event.getOption("day-of-the-week").getAsString();
                time = event.getOption("time").getAsString();
                duration = event.getOption("duration").getAsInt();
            } catch (NullPointerException e) {
                event.reply("Please provide all required parameters").setEphemeral(true).queue();
                return;
            }

            String description = event.getOption("description", "", OptionMapping::getAsString);

            if (Bot.getScheduleManager().getScheduleComposite(event.getUser().getId())
                    .createOrGetSchedule(scheduleName)
                    .getEvent(eventName) != null) {
                event.reply("Schedule event with name: "+eventName+" already exists!").setEphemeral(true).queue();
                return;
            }

            Bot.getScheduleManager().getScheduleComposite(event.getUser().getId())
                    .createOrGetSchedule(scheduleName)
                    .updateEvent(eventName, day, time, duration, description);
            event.reply("Schedule *"+scheduleName+"* added with event "+eventName+"!").setEphemeral(true).queue();

            return;
        }

        if (event.getName().equals("update-schedule")) {
            // update should allow to change the name of the event, duration, time, description, day

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

            String day = event.getOption("day-of-the-week", scheduleEvent.getDay(), OptionMapping::getAsString);
            String time = event.getOption("time", scheduleEvent.getTime(), OptionMapping::getAsString);
            Integer duration = event.getOption("duration", scheduleEvent.getDuration(), OptionMapping::getAsInt);
            String description = event.getOption("description", scheduleEvent.getDescription(), OptionMapping::getAsString);

            schedule.updateEvent(eventName, day, time, duration, description);

            event.reply("Schedule *" + scheduleName + "* event *" + eventName + "* updated!").setEphemeral(true).queue();

        }

        if (event.getName().equals("delete-schedule")) {

            String scheduleName;
            try {
                scheduleName = event.getOption("schedule-name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name").setEphemeral(true).queue();
                return;
            }

            String err = Bot.getScheduleManager().getScheduleComposite(event.getUser().getId())
                    .deleteSchedule(scheduleName);
            if (err == null) {
                event.reply("Schedule *" + scheduleName + "* deleted.").setEphemeral(true).queue();
            } else {
                event.reply("Could not delete the schedule *" + scheduleName + "*, because *" + err + "*").setEphemeral(true).queue();
            }

        }
        if (event.getName().equals("delete-schedule-event")) {

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

            String err = schedule.deleteEvent(eventName);
            if (err == null) {
                event.reply("Schedule *" + scheduleName + "* event *" + eventName + "* deleted.").setEphemeral(true).queue();
            } else {
                event.reply("Could not delete the schedule *" + scheduleName + "* event *"+eventName+"*, because *" + err + "*").setEphemeral(true).queue();
            }
        }
    }
}
