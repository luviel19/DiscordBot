package main.com.company.luviel19;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import main.com.company.luviel19.commands.Help;
import main.com.company.luviel19.commands.Info;
import main.com.company.luviel19.commands.NameServers;
import main.com.company.luviel19.commands.botinfo;
import main.com.company.luviel19.lavaplayer.Disconnect;
import main.com.company.luviel19.utills.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.LoggerFactory;

public class MyBot extends ListenerAdapter implements EventListener {


    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("net.dv8tion").setLevel(Level.WARN); // Установите нужный уровень  логирования
        loggerContext.getLogger("org.apache.http").setLevel(Level.ERROR);

        JDA jda = JDABuilder.createDefault(token.getToken())
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES,GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES)// enables explicit access to message.getContentDisplay()
                .addEventListeners(new Pricorm(), new MyBot(), new Info(), new Help(), new botinfo(),
                        new ping(), new music(), new gpt(),new NameServers(),new Button())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setActivity(Activity.playing("Settings"))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setBulkDeleteSplittingEnabled(true)

                .build();
        jda.updateCommands().addCommands(
                Commands.slash("ping", "Calculate ping of the bot"),

                Commands.slash("help", "help command"),
              
                Commands.slash("info", "Information about  server"),

                Commands.slash("play", "The name of the song")
                        .addOption(OptionType.STRING ,"music", "find music name",true),
                Commands.slash("skip", "skip music"),
                Commands.slash("stop", "stop music"),

                Commands.slash("bot", "Information about  bot")

                        //.setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)) // only usable with ban permissions
                        // Ban command only works inside a guild

        ).queue();

        //You can also add event listeners to the already built JDA instance

        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
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
