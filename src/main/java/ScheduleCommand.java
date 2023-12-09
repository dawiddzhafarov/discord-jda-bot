import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.List;

public class ScheduleCommand extends ListenerAdapter {

    public void init(){
        Bot.jda.upsertCommand("set-schedule", "Creates the schedule with given event")
                .addOption(OptionType.STRING, "Schedule Name", "Name of the schedule")
                .addOption(OptionType.STRING, "Event Name", "Name of the event")
                .addOption(OptionType.STRING, "Day of the Week", "Day of the week")
                .addOption(OptionType.STRING, "Time", "Time of the event")
                .addOption(OptionType.INTEGER, "Duration", "Duration of the event")
                .addOption(OptionType.STRING, "Description", "Description of the event").queue();

        Bot.jda.upsertCommand("update-schedule", "Updates the schedule")
                .addOption(OptionType.STRING, "Schedule Name", "Name of the schedule")
                .addOption(OptionType.STRING, "Event Name", "Name of the event")
                .addOption(OptionType.STRING, "Day of the Week", "Day of the week")
                .addOption(OptionType.STRING, "Time", "Time of the event")
                .addOption(OptionType.INTEGER, "Duration", "Duration of the event")
                .addOption(OptionType.STRING, "Description", "Description of the event").queue();

        Bot.jda.upsertCommand("delete-schedule", "Deletes the schedule")
                .addOption(OptionType.STRING, "Schedule Name", "Name of the schedule").queue();
    }



    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-schedule")) {

            //baza = SuperBaza();
            event.reply("pong").queue();
//            List<OptionMapping> args = event.getOptions();
//            args.get(0);

            String scheduleName = event.getOption("Schedule Name").getAsString();
            String eventName = event.getOption("Event Name").getAsString();
            String day = event.getOption("Day of the Week").getAsString();
            String time = event.getOption("Time").getAsString();
            Integer duration = event.getOption("Duration").getAsInt();
            String description = event.getOption("Description").getAsString();

            // ID usera + scheduleName = klucz w bazie danych
            String userID = event.getUser().getId();

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

            Integer unprovidedOptionsCounter = 0;
            OptionMapping scheduleNameOptionMapping = event.getOption("Schedule Name");
            if (scheduleNameOptionMapping == null) {
                unprovidedOptionsCounter++;
                // check if schedule entry exists, if not, error out
            } else {
                String scheduleName = scheduleNameOptionMapping.getAsString(); // should be required
            }
            OptionMapping eventNameOptionMapping = event.getOption("Event Name");
            if (eventNameOptionMapping == null) {
                unprovidedOptionsCounter++;
            } else {
                String eventName = eventNameOptionMapping.getAsString();
            }
            OptionMapping dayOptionMapping = event.getOption("Day of the Week");
            if (dayOptionMapping == null) {
                unprovidedOptionsCounter++;
            } else {
                String day = dayOptionMapping.getAsString();
            }
            OptionMapping timeOptionMapping = event.getOption("Time");
            if (timeOptionMapping == null) {
                unprovidedOptionsCounter++;
            } else {
                String time = timeOptionMapping.getAsString();
            }
            OptionMapping durationOptionMapping = event.getOption("Duration");
            if (durationOptionMapping == null) {
                unprovidedOptionsCounter++;
            } else {
                Integer duration = durationOptionMapping.getAsInt();
            }
            OptionMapping descriptionOptionMapping = event.getOption("Description");
            if (descriptionOptionMapping == null) {
                unprovidedOptionsCounter++;
            } else {
                String description = descriptionOptionMapping.getAsString();
            }
            if (unprovidedOptionsCounter == 6) {
                event.reply("While using '/update-schedule', you have to provide at least one option").queue();
            } else {
                String userID = event.getUser().getId();

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
        }
        if (event.getName().equals("delete-schedule")) {

            //baza = SuperBaza();
            OptionMapping scheduleNameOptionMapping = event.getOption("Schedule Name");
            if (scheduleNameOptionMapping == null) {
                event.reply("Schedule name is required in order to delete the schedule!").queue();
            } else {
                String scheduleName = scheduleNameOptionMapping.getAsString();
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
            OptionMapping scheduleNameOptionMapping = event.getOption("Schedule Name");
            if (scheduleNameOptionMapping == null) {
                event.reply("Schedule name is required in order to delete the schedule!").queue();
            } else {
                String scheduleName = scheduleNameOptionMapping.getAsString();
            }
            OptionMapping eventNameOptionMapping = event.getOption("Event Name");
            if (eventNameOptionMapping == null) {
                event.reply("Event name is required in order to delete the event!").queue();
            } else {
                String eventName = eventNameOptionMapping.getAsString();
            }

            String userID = event.getUser().getId();

//          entries, err = baza.Delete(user=userID, scheduleName=scheduleName)
 //           struct = json.Parse(entries);
//            struct.remove(eventName);
//            baza.update(userID, scheduleName, structJson);
        }
    }



}
