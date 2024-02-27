package main.com.company.luviel19.lavaplayer;

import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;


public class Disconnect extends ListenerAdapter {



    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent voice) {
        AudioChannelUnion x = voice.getChannelLeft();
        if (x!=null) {
            int disconnectedChannel = voice.getChannelLeft().getMembers().size();
            AudioManager audioManager = voice.getGuild().getAudioManager();

            if (disconnectedChannel == 1) {
                System.out.println(x);
                if (voice.getChannelLeft().getMembers().get(0).getId().equals(voice.getJDA().getSelfUser().getId())) {
                    audioManager.closeAudioConnection();


                }
            }
        }
    }
}