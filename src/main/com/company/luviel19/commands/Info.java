package main.com.company.luviel19.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Info extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String msg = event.getName().toLowerCase();
        if (msg.equalsIgnoreCase("info") || msg.equalsIgnoreCase("?info")) {

            event.replyEmbeds(serverInfo(event.getGuild()).build()).queue();
        }
    }

    private static EmbedBuilder serverInfo(Guild guild) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        OffsetDateTime date = guild.getTimeCreated();
        String formated = df.format(date);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Information about server:");
        builder.setAuthor(guild.getName());
        //builder.setDescription("123");
        builder.addField(":crown: Owner:", "```"+guild.getOwner().getUser().getName()+"```", true);

        builder.addField(":bust_in_silhouette: Members:", "```" + String.valueOf(guild.getMemberCount())+"```", true);
        builder.addBlankField(true);
        builder.addField(":date: Server create:", "```"+formated+"```", true);
        builder.addField(":tada: Booster:","```" + String.valueOf(guild.getBoostCount())+"```",true);
        builder.setThumbnail(guild.getIconUrl());
        return builder;
    }
}


