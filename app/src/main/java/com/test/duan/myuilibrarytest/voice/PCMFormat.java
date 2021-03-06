package com.test.duan.myuilibrarytest.voice;

/**
 * Created by Duanyy on 2016/6/24.
 */
public enum PCMFormat {
    PCM_8BIT(1, 3),
    PCM_16BIT(2, 2);

    private int bytesPerFrame;
    private int audioFormat;

    private PCMFormat(int bytesPerFrame, int audioFormat) {
        this.bytesPerFrame = bytesPerFrame;
        this.audioFormat = audioFormat;
    }

    public int getBytesPerFrame() {
        return this.bytesPerFrame;
    }

    public int getAudioFormat() {
        return this.audioFormat;
    }

}
