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
        helpers.addField("?Рыба", "```Даст информацию по рыбе,которую вы введете```", true);
        helpers.addBlankField(true);
        helpers.addBlankField(true);
        helpers.addField("?Водоем","```Даст информацию про водоем(в будущем)```",true);
        helpers.addBlankField(true);
        helpers.addBlankField(true);
        helpers.addField("?info","```Даст информацию по серверу```",true);
        helpers.setThumbnail(guild.getIconUrl());
        return helpers;
    }
}
