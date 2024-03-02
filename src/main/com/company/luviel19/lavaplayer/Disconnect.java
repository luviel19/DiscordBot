package main.com.company.luviel19.lavaplayer;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;


public class Disconnect extends ListenerAdapter {




    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent voice) {
        AudioChannelUnion x = voice.getChannelLeft();
        GuildVoiceState userVoiceState = voice.getMember().getVoiceState();
        GuildVoiceState botVoiceState = voice.getGuild().getSelfMember().getVoiceState();
        if (x!=null) {
            int disconnectedChannel = voice.getChannelLeft().getMembers().stream().filter(member -> !member.getUser().isBot()).toList().size();
            AudioManager audioManager = voice.getGuild().getAudioManager();

            if (disconnectedChannel == 0) {
                System.out.println(disconnectedChannel);
                    audioManager.closeAudioConnection();
                    PlayerManager.getInstance().getMusicManager(voice.getGuild()).trackScheduler.audioPlayer.destroy();


            }
        }
    }
}