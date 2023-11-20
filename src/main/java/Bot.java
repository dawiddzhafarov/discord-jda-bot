import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    static public JDA jda;

    static private PingCommand pingCommand;

    public static void main(String args[]) {
        JDABuilder builder = JDABuilder.createDefault("token");

        try {
            
            jda = builder.build().awaitReady();

            pingCommand = new PingCommand();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
