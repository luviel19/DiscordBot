package main.com.company.luviel19.commands;

import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class mute extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split("\\\\s+");
        if (message[0].equalsIgnoreCase("!mute")) { // Проверяем, если сообщение начинается с \"!mute\"
            if (message.length > 1) {
                String userId = message[1].replace("<@", " ").replace(">", " "); // Получаем ID пользователя, которого нужно замутить
                event.getGuild().kick(UserSnowflake.fromId(userId)).queue(); // Добавляем роль мута пользователю
                event.getChannel().sendMessage("Пользователь замучен!").queue();
            } else {
                event.getChannel().sendMessage("Вы должны указать пользователя для мута!").queue();
            }
        }
    }
}