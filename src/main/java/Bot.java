import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    static public JDA jda;

    static private PingCommand pingCommand;

    public static void main(String args[]) {
        JDABuilder builder = JDABuilder.createDefault("token");

        pingCommand = new PingCommand();

        builder.addEventListeners(pingCommand);

        try {

            jda = builder.build().awaitReady();

            pingCommand.init();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
