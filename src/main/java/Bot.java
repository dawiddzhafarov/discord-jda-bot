import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    static public JDA jda;

    static private PingCommand pingCommand;
    static private ScheduleCommand scheduleCommand;

    static private Database database;

    static private ScheduleManager scheduleManager;

    public static void main(String args[]) {
        JDABuilder builder = JDABuilder.createDefault("MTE3NDA0MTM1MzYxMzc1NDQyMA.GFLQWv.IMqPj9_uHy6Uj4c2on3qigRA_n9UEcKlRKa4Zw");

        pingCommand = new PingCommand();
        scheduleCommand = new ScheduleCommand();

        builder.addEventListeners(scheduleCommand, pingCommand);

        try {

            database = new Database();
            if (!database.isInitialised())
                return;
            System.out.println("Database connected.");

            scheduleManager = new ScheduleManager();
            if (!scheduleManager.init())
                return;
            System.out.println("ScheduleManager loaded.");


            jda = builder.build().awaitReady();

            pingCommand.init();
            scheduleCommand.init();
            System.out.println("Commands initialised.");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static Database getDatabase() {
        return database;
    }

    public static ScheduleManager getScheduleManager() {
        return scheduleManager;
    }
}
