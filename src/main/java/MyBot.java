import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.testng.TestListenerAdapter;

import java.io.PrintStream;

public class MyBot extends ListenerAdapter implements EventListener {
    public static void main(String[] args) {
        JDA jda = JDABuilder.createDefault("MTE4MDAyOTIxNzU5ODA4MzE5NA.G3e57L.dPitQGGglON3JpgiX7PUFoN7Sp1LXJ6O-HcTFY")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_PRESENCES)// enables explicit access to message.getContentDisplay()
                .addEventListeners(new test(),new MyBot(),new Help())
                .setActivity(Activity.playing("Fisher Online"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new MyBot());
    }


    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }
    }
}
