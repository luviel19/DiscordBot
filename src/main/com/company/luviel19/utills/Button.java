package main.com.company.luviel19.utills;

import main.com.company.luviel19.lavaplayer.Destroy;
import main.com.company.luviel19.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.stringtemplate.v4.ST;

public class Button extends ListenerAdapter {

    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent voice) {
       PlayerManager.getInstance().getMusicManager(voice.getGuild()).trackScheduler.audioPlayer.destroy();
    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("skip")) {
            PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.nextTrack();
            event.reply("Успех! Трек пропущен!").queue();
        } else if (event.getComponentId().equals("stop")) {
            PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
            event.reply("Успех! Музыка больше не играет!").complete();
        }else if(event.getComponentId().equals("leave")){

        }

    }
}
