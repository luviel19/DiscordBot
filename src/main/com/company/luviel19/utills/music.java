package main.com.company.luviel19.utills;

import main.com.company.luviel19.lavaplayer.Leave;
import main.com.company.luviel19.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;


public class music extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String commandName = event.getFullCommandName();
        GuildVoiceState userVoiceState = event.getMember().getVoiceState();
        GuildVoiceState botVoiceState = event.getGuild().getSelfMember().getVoiceState();
        AudioManager audioManager = event.getGuild().getAudioManager();


        Leave leaveInstance = new Leave();
        Thread thread = new Thread(() -> {
            if (commandName.equals("play")) {

                String song = "play " + event.getOption("music").getAsString();

                if (!userVoiceState.inAudioChannel()) {
                    event.reply("Вы должны быть в голосовом канале!").queue();
                } else {

                    if (!botVoiceState.inAudioChannel()) {
                        VoiceChannel voiceChannel = event.getGuild().getVoiceChannels().get(0);
                        leaveInstance.startDisconnectTimer(voiceChannel);
                        audioManager.openAudioConnection(userVoiceState.getChannel());
                        audioManager.setSelfDeafened(true);

                        event.reply("сейчас играет:").queue();

                    }
                    String link = String.join(" ", song);
                    if (!isUrl(link)) {
                        link = "ytsearch:" + link;
                        event.reply("следующая песня:").queue();
                    }
                    PlayerManager.getInstance().loadAndPlay(event.getChannel().asTextChannel(), link);
                }
            }
            if (commandName.equals("skip")) {
                checkAudioChannel(userVoiceState, botVoiceState, commandName);

                PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.nextTrack();
                event.reply("Успех! Трек пропущен!").queue();
            }

            if (commandName.equals("stop")) {
                checkAudioChannel(userVoiceState, botVoiceState, commandName);

                audioManager.closeAudioConnection();
                PlayerManager.getInstance().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.destroy();
                event.reply("Успех! Музыка больше не играет!").complete();
            }
        });
        thread.start();
    }


    public static void checkAudioChannel(GuildVoiceState userVoiceState, GuildVoiceState botVoiceState,String commandName) {
        if (!userVoiceState.inAudioChannel()) {
        }
        if (!botVoiceState.inAudioChannel()) {
        }
    }

    public boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
