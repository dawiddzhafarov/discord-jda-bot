import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    static public JDA jda;

    static private PingCommand pingCommand;
    static private ScheduleCommand scheduleCommand;

    public static void main(String args[]) {
        JDABuilder builder = JDABuilder.createDefault("MTE3NDA0MTM1MzYxMzc1NDQyMA.G3RYpb.XJimBwFjxUhxgTDX9Avvb8pwrcu0YWxdD2kOzA");

        pingCommand = new PingCommand();
        scheduleCommand = new ScheduleCommand();

        builder.addEventListeners(scheduleCommand, pingCommand);

        try {

            jda = builder.build().awaitReady();

            pingCommand.init();
            scheduleCommand.init();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
