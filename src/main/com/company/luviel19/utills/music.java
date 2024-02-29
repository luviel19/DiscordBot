package main.com.company.luviel19.utills;


import ch.qos.logback.classic.spi.LoggingEventVO;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.com.company.luviel19.lavaplayer.AudioPlayerSendHandler;
import main.com.company.luviel19.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class music extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String commandName = event.getFullCommandName();
        GuildVoiceState userVoiceState = event.getMember().getVoiceState();
        GuildVoiceState botVoiceState = event.getGuild().getSelfMember().getVoiceState();
        AudioManager audioManager = event.getGuild().getAudioManager();



        Thread thread = new Thread(() -> {
            if (commandName.equals("play")) {

                String song = "play " + event.getOption("music").getAsString();

                if (!userVoiceState.inAudioChannel()) {
                    event.reply("Вы должны быть в голосовом канале!").queue();
                } else {
                    String link2 = String.join(" ", song);
                    if (!botVoiceState.inAudioChannel()) {
                        VoiceChannel voiceChannel = event.getGuild().getVoiceChannels().get(0);
                        audioManager.openAudioConnection(userVoiceState.getChannel());
                        audioManager.setSelfDeafened(true);
                        event.reply("сейчас играет:").queue();

                    }//
                    String link = String.join(" ", song);
                    if (!isUrl(link)) {

                        link = "ytsearch:" + link;
                        PlayerManager playerManager = new PlayerManager();
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

    private static EmbedBuilder Music(Guild guild, List<AudioTrack> tracks) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setTitle("Music Player:");
        builder.setAuthor(guild.getName());
        //builder.setDescription("123");
        builder.addField("Сейчас играет трек:", "```" +tracks.get(0).getInfo().title+ "```"
                , true);
        builder.addBlankField(true);
        builder.addBlankField(true);
        builder.addField("Исполнитель:", "```" + tracks.get(0).getInfo().author + "```", true);
        builder.addField("Следующая песня:", "```" + tracks.get(1).getInfo().title + "```", true);
        //builder.setThumbnail();
        return builder;
    }
}
