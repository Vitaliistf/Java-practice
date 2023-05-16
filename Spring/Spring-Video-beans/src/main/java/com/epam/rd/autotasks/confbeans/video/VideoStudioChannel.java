package com.epam.rd.autotasks.confbeans.video;

import java.util.stream.Stream;

public class VideoStudioChannel extends Channel {
    private VideoStudio videoStudio;

    public VideoStudioChannel(VideoStudio videoStudio) {
        this.videoStudio = videoStudio;
    }

    public Video produce() {
        Video video = new Video("Cat Failure Compilation", videoStudio.produce().getPubTime());
        this.addVideo(video);
        return video;
    }

    @Override
    public Stream<Video> videos() {
        for (int i = 0; i < 7; i++) {
            this.produce();
        }
        return super.videos().skip(super.videos().count() - 7);
    }
}
