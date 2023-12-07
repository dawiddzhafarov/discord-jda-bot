import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.List;

public class ScheduleCommand extends ListenerAdapter {

    public void init(){
        Bot.jda.upsertCommand("set-schedule", "Sets the schedule")
                .addOption(OptionType.STRING, "schedule-name", "dsa")
                .addOption(OptionType.STRING, "event-name", "dsa")
                .addOption(OptionType.STRING, "day-of-the-week", "dsa")
                .addOption(OptionType.STRING, "time", "dsa")
                .addOption(OptionType.INTEGER, "duration", "dsa")
                .addOption(OptionType.STRING, "description", "dsa")
                .queue();

        Bot.jda.upsertCommand("update-schedule", "Updates the event in schedule")
                .addOption(OptionType.STRING, "schedule-name", "dsa")
                .addOption(OptionType.STRING, "event-mame", "dsa")
                .addOption(OptionType.STRING, "day-of-the-week", "dsa")
                .addOption(OptionType.STRING, "time", "dsa")
                .addOption(OptionType.INTEGER, "duration", "dsa")
                .addOption(OptionType.STRING, "description", "dsa")
                .queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-schedule")) {

            //baza = SuperBaza();
            event.reply("pong").queue();
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
            // ID usera + scheduleName = klucz w bazie danych
            String userID = event.getUser().getId();
//            String dbKey = userID + scheduleName;

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
