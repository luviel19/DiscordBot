package main.com.company.luviel19.lavaplayer;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;


public class Destroy {
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent voice) {
        PlayerManager.getInstance().getMusicManager(voice.getGuild()).trackScheduler.audioPlayer.destroy();
    }
}
