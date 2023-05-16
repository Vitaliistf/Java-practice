package com.epam.rd.autotasks.confbeans.config;

import com.epam.rd.autotasks.confbeans.video.Channel;
import com.epam.rd.autotasks.confbeans.video.Video;
import com.epam.rd.autotasks.confbeans.video.VideoStudio;
import com.epam.rd.autotasks.confbeans.video.VideoStudioChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

@Configuration
public class ChannelWithInjectedPrototypeVideoConfig {

    @Bean
    @Scope("prototype")
    public Video video() {
        return channel().produce();
    }

    @Bean
    @Scope("singleton")
    public VideoStudioChannel channel() {
        return new VideoStudioChannel(new VideoStudio() {
            private LocalDateTime date = LocalDateTime.now();
            @Override
            public Video produce() {
                date = date.plusDays(1);
                return new Video("Cat Failure Compilation", date);
            }
        });
    }

}
