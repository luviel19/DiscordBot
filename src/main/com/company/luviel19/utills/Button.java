package main.com.company.luviel19.utills;
import main.com.company.luviel19.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Objects;

public class Button extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        User user = event.getUser();

        if (event.getComponentId().equals("skip")) {
            event.deferEdit().setContent("Новая надпись под кнопкой").queue();
            if (audioManager.getConnectionStatus() == ConnectionStatus.CONNECTED
                    && audioManager.getConnectedChannel().getMembers().contains(event.getGuild().getSelfMember())) {
                PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.nextTrack();
                event.reply("Успех! Трек пропущен!").queue();
            }
        }


        if (event.getComponentId().equals("stop")) {
            if (audioManager.getConnectionStatus() == ConnectionStatus.CONNECTED
                    && audioManager.getConnectedChannel().getMembers().contains(event.getGuild().getSelfMember())) {
                PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
                event.reply("Успех! Музыка больше не играет!").queue();
            }


            if (event.getComponentId().equals("leave")) {
                if (audioManager.getConnectionStatus() == ConnectionStatus.CONNECTED
                        && audioManager.getConnectedChannel().getMembers().contains(event.getGuild().getSelfMember())) {
                    event.getGuild().getAudioManager().closeAudioConnection();
                    PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
                    event.reply("Бот вышел!").queue();

                }
            }
        }
    }
}


