package main.com.company.luviel19.lavaplayer;


import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Leave extends ListenerAdapter {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public void startDisconnectTimer(@NotNull VoiceChannel channel) {


        scheduler.schedule(() -> {

            if (channel.getGuild().getChannels().size()-5 <= 1) {
                System.out.println("123");// Проверка, что в голосовом канале остался только бот
                if (channel.getGuild().getAudioManager().isConnected()) {
                    System.out.println("321");// Проверка, что бот подключен к голосовому каналу
                    channel.getGuild().getAudioManager().closeAudioConnection(); // Отключение бота от голосового канала
                }
            }

            }, 5, TimeUnit.SECONDS);


        }


}
