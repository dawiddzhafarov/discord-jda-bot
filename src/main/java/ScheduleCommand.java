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
                .addOption(OptionType.STRING, "Description", "Description of the event")
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
            try {
                String scheduleName = event.getOption("schedule-name").getAsString();
                String eventName = event.getOption("event-name").getAsString();
                String day = event.getOption("day-of-the-week").getAsString();
                String time = event.getOption("time").getAsString();
                Integer duration = event.getOption("duration").getAsInt();
            } catch (NullPointerException e) {
                event.reply("Please provide all required parameters").queue();
                return;
            }

            String description = event.getOption("description", "", OptionMapping::getAsString);

//             entry = baza.select(user=userID, scheduleName=scheduleName)
//             if entry exist {
//                entry += dane
//                baza.update(where user = userID, scheduleName=..., entry
//             } else {
//             baza.insert(where user = userID, scheduleName=..., entry

            return;
        }

        if (event.getName().equals("update-schedule")) {
            // update should allow to change the name of the event, duration, time, description, day

            //baza = SuperBaza();
            try {
                String scheduleName = event.getOption("schedule-name").getAsString();
                String eventName = event.getOption("event-name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name and Event Name").queue();
                return;
            }
            //From baza get all
            String currentDay = null;
            String currentTime = null;
            Integer currentDuration = null;
            String currentDescription = null;

            String day = event.getOption("day-of-the-week", currentDay, OptionMapping::getAsString);
            String time = event.getOption("time", currentTime, OptionMapping::getAsString);
            Integer duration = event.getOption("duration", currentDuration, OptionMapping::getAsInt);
            String description = event.getOption("description", currentDescription, OptionMapping::getAsString);


//              baza = SuperBaza();
//              entries = baza.Select(user=userID, scheduleName=scheduleName)
//              if entries exist {
//                  for entry : entries:
//                        if (entry.EventName == eventName) {
//                            //perform updates on this event
//                        }
//              } else {
//              event.reply("Schedule with name: %s does not exist!", scheduleName).queue();
//                return;
        }
        if (event.getName().equals("delete-schedule")) {

            //baza = SuperBaza();

            try {
                String scheduleName = event.getOption("schedule-name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name").queue();
                return;
            }

            String userID = event.getUser().getId();

//          entry, err = baza.Delete(user=userID, scheduleName=scheduleName)
//            if entry {
//                return;
//            } else {
//                event.reply("could not delete the schedule entry: %s", err).queue();
//            }
//            return;
        }
        if (event.getName().equals("delete-schedule-event")) {

            //baza = SuperBaza();

            try {
                String scheduleName = event.getOption("schedule-name").getAsString();
                String eventName = event.getOption("event-name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name and Event Name").queue();
                return;
            }

            String userID = event.getUser().getId();

//          entries, err = baza.Delete(user=userID, scheduleName=scheduleName)
 //           struct = json.Parse(entries);
//            struct.remove(eventName);
//            baza.update(userID, scheduleName, structJson);
        }
    }
}
