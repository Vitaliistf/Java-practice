package com.epam.rd.autotasks.confbeans.config;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import com.epam.rd.autotasks.confbeans.video.VideoStudio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

@Configuration
public class ChannelWithPhantomVideoStudioConfig {

    private int movie = 0;
    private LocalDateTime date = LocalDateTime.of(1999, 10, 18, 10, 0);

    @Bean
    @Scope("prototype")
    public Video video() {
        movie++;
        date = date.plusYears(2);
        return new Video("Cat & Curious " + movie, date);
    }

    @Bean
    public Channel channel() {
        Channel channel = new Channel();
        for(int i = 0; i < 8; i++) {
            channel.addVideo(video());
        }
        return channel;
    }
}
