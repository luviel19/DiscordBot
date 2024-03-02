package main.com.company.luviel19.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;

public class Help extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String slash = event.getName().toLowerCase();
        if (slash.equals("help")) {
            event.replyEmbeds(Helper(event.getGuild()).build()).queue();
        }
    }

    private static EmbedBuilder Helper(Guild guild) {
        EmbedBuilder helpers = new EmbedBuilder();
        helpers.setColor(Color.RED);
        helpers.setTitle("мои команды:");
        helpers.setAuthor(guild.getName());
        //builder.setDescription("123");
        helpers.addField("```Ping:```","</ping:1205993329062318180>",false);
        helpers.addField("```Команды:```","</help:1205993620751126640>",false);
        helpers.addField("```Информация о боте```","</bot:1205998588505620531>",false);
        helpers.addField("```Информация о сервере```", "</info:1206006732350824488>",false);
        helpers.addField("```музыка```","</play:1209901688161312783>, </skip:1209901688685862942> , </stop:1209901688685862943>",false);
        helpers.setThumbnail(guild.getIconUrl());
        return helpers;
    }
}
