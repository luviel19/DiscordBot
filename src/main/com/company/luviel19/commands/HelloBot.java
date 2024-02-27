package main.com.company.luviel19.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelloBot extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("hello")) {
            event.reply("Click the button to say hello")
                    .addActionRow(
                            Button.primary("hello", "Click Me"), // Button with only a label
                            Button.success("emoji","<:minn:245267426227388416>")) // Button with only an emoji
                    .queue();

        }
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("hello")) {
            event.reply("Hello :)").queue();
        }
    }
}