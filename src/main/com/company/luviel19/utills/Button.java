package main.com.company.luviel19.utills;
import main.com.company.luviel19.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Button extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (event.getComponentId().equals("skip")) {
            PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.nextTrack();
            event.reply("Успех! Трек пропущен!").queue();
        } else if (event.getComponentId().equals("stop")) {
            PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
            event.reply("Успех! Музыка больше не играет!").queue();
        } else if (event.getComponentId().equals("leave")) {
           event.getGuild().getAudioManager().closeAudioConnection();
           PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
           event.reply("Бот вышел!").queue();

        }
    }
}


