package main.com.company.luviel19.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager extends ListenerAdapter {
    private static PlayerManager Instance;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String url) {
        final GuildMusicManager guildMusicManager = getMusicManager(channel.getGuild());
        audioPlayerManager.loadItemOrdered(musicManagers, url, new AudioLoadResultHandler() {


            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                guildMusicManager.trackScheduler.queue(audioTrack);

                channel.sendMessage("Песня: " + audioTrack.getInfo().title + "\n) Исполнитель " + audioTrack.getInfo().author).queue();

            }


@Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
               final List<AudioTrack> tracks = audioPlaylist.getTracks();

                if (!tracks.isEmpty()) {
                    guildMusicManager.trackScheduler.queue(tracks.get(1));
                    channel.sendMessageEmbeds(Music(channel.getGuild(),tracks).build()).addActionRow(
                            Button.primary("skip", "skip").withEmoji(Emoji.fromFormatted("⏩")), // Button with only a label
                            Button.danger("stop","stop").withEmoji(Emoji.fromFormatted("⛔")))
                            //Button.secondary("leave","leave"))
                            // Button with only an emoji
                            .queue();


                }

            }



            @Override
            public void noMatches() {
            }

            @Override
            public void loadFailed(FriendlyException e) {
            }
        });
    }


    public static PlayerManager getInstance() {
        if (Instance == null) {
            Instance = new PlayerManager();
        }
        return Instance;
    }

    private static EmbedBuilder Music(Guild guild,List<AudioTrack> tracks) {
        EmbedBuilder builder = new EmbedBuilder();
        long milliseconds = tracks.get(0).getDuration();
        int seconds = (int) (milliseconds / 1000); // пример количества секунд

        int minutes = seconds / 60; // получение количества минут из секунд
        int hours = minutes / 60; // получение количества часов из минут

        int remainingMinutes = minutes % 60; // получение оставшихся минут после вычисления часов
        int remainingSeconds = seconds % 60; // получение оставшихся секунд после вычисления минут

        String formattedTime = String.format("%d часов, %d минут, %d секунд", hours, remainingMinutes, remainingSeconds);
        builder.setColor(Color.RED);
        builder.setTitle("Music Player:");
        builder.setAuthor(guild.getName());
        //builder.setDescription("123");
        builder.addField("Трек:", "```" +tracks.get(0).getInfo().title+ "```"
                , true);
        builder.addBlankField(true);
        builder.addBlankField(true);
        builder.addField("Исполнитель:", "```" + tracks.get(0).getInfo().author + "```", true);
        builder.addBlankField(true);
        builder.addBlankField(true);
        builder.addField("Продолжительность:", "```" + formattedTime + "```", true);
        Button button1 = Button.success("skip","skip");
       //builder.setThumbnail();
        return builder;
    }
}