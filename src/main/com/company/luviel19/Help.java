package main.com.company.luviel19;

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
        helpers.addField("/bot", "```Информация о боте```", true);
        helpers.addBlankField(true);
        helpers.addBlankField(true);
        helpers.addField("/info","```Информация о сервере```",true);
        helpers.addBlankField(true);
        helpers.addBlankField(true);
        helpers.addField("/ban","```Бан```",true);
        helpers.addBlankField(true);
        helpers.addBlankField(true);
        helpers.addField("?play music","```включение музыки```",true);
        helpers.setThumbnail(guild.getIconUrl());
        return helpers;
    }
}
