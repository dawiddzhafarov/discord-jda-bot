import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.List;

public class ScheduleCommand extends ListenerAdapter {

    public void init(){
        Bot.jda.upsertCommand("set-schedule", "Sets the schedule")
                .addOption(OptionType.STRING, "Schedule Name", "dsa")
                .addOption(OptionType.STRING, "Event Name", "dsa")
                .addOption(OptionType.STRING, "Day of the Week", "dsa")
                .addOption(OptionType.STRING, "Time", "dsa")
                .addOption(OptionType.INTEGER, "Duration", "dsa")
                .addOption(OptionType.STRING, "Description", "dsa")
                .queue();

        Bot.jda.upsertCommand("update-schedule", "Updates the event in schedule")
                .addOption(OptionType.STRING, "Schedule Name", "dsa")
                .addOption(OptionType.STRING, "Event Name", "dsa")
                .addOption(OptionType.STRING, "Day of the Week", "dsa")
                .addOption(OptionType.STRING, "Time", "dsa")
                .addOption(OptionType.INTEGER, "Duration", "dsa")
                .addOption(OptionType.STRING, "Description", "dsa")
                .queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-schedule")) {

            //baza = SuperBaza();
            event.reply("pong").queue();
            try {
                String scheduleName = event.getOption("Schedule Name").getAsString();
                String eventName = event.getOption("Event Name").getAsString();
                String day = event.getOption("Day of the Week").getAsString();
                String time = event.getOption("Time").getAsString();
                Integer duration = event.getOption("Duration").getAsInt();
            } catch (NullPointerException e) {
                event.reply("Please provide all required parameters").queue();
                return;
            }

            String description = event.getOption("Description", "", OptionMapping::getAsString);
            // ID usera + scheduleName = klucz w bazie danych
            String userID = event.getUser().getId();
            String dbKey = userID + scheduleName;

//             entry = baza.select(user=userID, scheduleName=scheduleName)
//             if entry exist {
//                entry += dane
//                baza.update(where user = userID, scheduleName=..., entry
//             } else {
//             baza.insert(where user = userID, scheduleName=..., entry

            return;
        }

        if (event.getName().equals("update-schedule")) {

            //baza = SuperBaza();
            OptionMapping scheduleNameOptionMapping = event.getOption("Schedule Name");
            try {
                String scheduleName = event.getOption("Schedule Name").getAsString();
                String eventName = event.getOption("Event Name").getAsString();
            } catch (NullPointerException e) {
                event.reply("Please provide Schedule Name and Event Name").queue();
                return;
            }
            //From baza get all
            String currentDay;
            String currentTime;
            Integer currentDuration;
            String currentDescription;

            String day = event.getOption("Day of the Week", currentDay, OptionMapping::getAsString);
            String time = event.getOption("Time", currentTime, OptionMapping::getAsString);
            Integer duration = event.getOption("Duration", currentDuration, OptionMapping::getAsInt);
            String description = event.getOption("Description", currentDescription, OptionMapping::getAsString);

// ID usera + scheduleName = klucz w bazie danych
//            String userID = event.getUser().getId();
//            String dbKey = userID + scheduleName;

//             entry = baza.select(user=userID, scheduleName=scheduleName)
//             if entry exist {
//                entry += dane
//                baza.update(where user = userID, scheduleName=..., entry
//             } else {
//             baza.insert(where user = userID, scheduleName=..., entry

            return;
        }
    }



}
