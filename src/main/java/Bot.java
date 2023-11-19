
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    static public JDA jda;

    public static void main(String args[]) {
        JDABuilder builder = JDABuilder.createDefault("token");


        jda = builder.build();
    }
}
