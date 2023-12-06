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
                .addOption(OptionType.STRING, "Description", "dsa").queue();

        Bot.jda.upsertCommand("update-schedule", "Updates the schedule")
                .addOption(OptionType.STRING, "Schedule Name", "dsa")
                .addOption(OptionType.STRING, "Event Name", "dsa")
                .addOption(OptionType.STRING, "Day of the Week", "dsa")
                .addOption(OptionType.STRING, "Time", "dsa")
                .addOption(OptionType.INTEGER, "Duration", "dsa")
                .addOption(OptionType.STRING, "Description", "dsa").queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("set-schedule")) {

            //baza = SuperBaza();
            event.reply("pong").queue();
            List<OptionMapping> args = event.getOptions();
            args.get(0);

            String scheduleName = event.getOption("Schedule Name").getAsString();
            String eventName = event.getOption("Event Name").getAsString();
            String day = event.getOption("Day of the Week").getAsString();
            String time = event.getOption("Time").getAsString();
            Integer duration = event.getOption("Duration").getAsInt();
            String description = event.getOption("Description").getAsString();

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
            List<OptionMapping> args = event.getOptions();
            OptionMapping scheduleNameOptionMapping = event.getOption("Schedule Name");
            if (scheduleNameOptionMapping == null) {
                // error
            } else {
                String scheduleName = scheduleNameOptionMapping.getAsString();
            }
            String eventName = event.getOption("Event Name").getAsString();
            String day = event.getOption("Day of the Week").getAsString();
            String time = event.getOption("Time").getAsString();
            Integer duration = event.getOption("Duration").getAsInt();
            String description = event.getOption("Description").getAsString();

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
