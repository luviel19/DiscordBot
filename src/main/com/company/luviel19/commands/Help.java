package main.com.company.luviel19.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
        helpers.addField("```Информация о боте```", "/bot", false);
        helpers.addField("```Информация о сервере```","/info",false);
        helpers.addField("```музыка```","/play , /skip , /stop",false);
        helpers.setThumbnail(guild.getIconUrl());
        return helpers;
    }
}
