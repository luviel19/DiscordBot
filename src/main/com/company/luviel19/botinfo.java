package main.com.company.luviel19;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class botinfo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String msg = event.getName().toLowerCase();
        if (msg.equals("bot")) {

            event.replyEmbeds(serverInfo(event.getGuild()).build())
                    .addActionRow(
                            Button.link("https://github.com/luviel19", "GitHub").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),// Button with only a label
                            Button.link(event.getJDA().getInviteUrl(), "InviteBot")).queue(); // Button with only a label
        }
    }

    private static EmbedBuilder serverInfo(Guild guild) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        OffsetDateTime date = guild.getTimeCreated();
        String formated = df.format(date);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Information about bot:");
        builder.setAuthor(guild.getName());
        //builder.setDescription("123");
        builder.addField(":crown: Bot owner:", "```" + "@luviel19"
                + "```", true);

        builder.addField("Servers", "```" + guild.getJDA().getGuilds().size() + "```", true);
        builder.addBlankField(true);
        builder.addField(":date: Bot crate", "```09.02.2024```", true);
        builder.setThumbnail("https://i.postimg.cc/TPbk9H01/luviel19.jpg");
        return builder;
    }
}

