package com.test.duan.myuilibrarytest.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Message;
import android.os.Process;

import com.moor.imkf.mp3recorder.util.LameUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Duanyy on 2016/6/24.
 */
public class MP3Recorder {

    private static final int DEFAULT_AUDIO_SOURCE = 1;
    private static final int DEFAULT_SAMPLING_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = 16;
    private static final PCMFormat DEFAULT_AUDIO_FORMAT;
    private static final int DEFAULT_LAME_MP3_QUALITY = 7;
    private static final int DEFAULT_LAME_IN_CHANNEL = 1;
    private static final int DEFAULT_LAME_MP3_BIT_RATE = 32;
    private static final int FRAME_COUNT = 160;
    private AudioRecord mAudioRecord = null;
    private int mBufferSize;
    private short[] mPCMBuffer;
    private DataEncodeThread mEncodeThread;
    private boolean mIsRecording = false;
    private File mRecordFile;
    private int mVolume;
    private static final int MAX_VOLUME = 2000;

    public MP3Recorder(File recordFile) {
        this.mRecordFile = recordFile;
    }

    public void start() throws IOException {
        if(!this.mIsRecording) {
            this.initAudioRecorder();
            this.mAudioRecord.startRecording();
            (new Thread() {
                public void run() {
                    Process.setThreadPriority(-19);
                    MP3Recorder.this.mIsRecording = true;

                    while(MP3Recorder.this.mIsRecording) {
                        int msg = MP3Recorder.this.mAudioRecord.read(MP3Recorder.this.mPCMBuffer, 0, MP3Recorder.this.mBufferSize);
                        if(msg > 0) {
                            MP3Recorder.this.mEncodeThread.addTask(MP3Recorder.this.mPCMBuffer, msg);
                            this.calculateRealVolume(MP3Recorder.this.mPCMBuffer, msg);
                        }
                    }

                    MP3Recorder.this.mAudioRecord.stop();
                    MP3Recorder.this.mAudioRecord.release();
                    MP3Recorder.this.mAudioRecord = null;
                    Message msg1 = Message.obtain(MP3Recorder.this.mEncodeThread.getHandler(), 1);
                    msg1.sendToTarget();
                }

                private void calculateRealVolume(short[] buffer, int readSize) {
                    int sum = 0;

                    for(int amplitude = 0; amplitude < readSize; ++amplitude) {
                        sum += buffer[amplitude] * buffer[amplitude];
                    }

                    if(readSize > 0) {
                        double var6 = (double)(sum / readSize);
                        MP3Recorder.this.mVolume = (int)Math.sqrt(var6);
                    }

                }
            }).start();
        }
    }

    public int getVolume() {
        return this.mVolume;
    }

    public int getMaxVolume() {
        return 2000;
    }

    public void stop() {
        this.mIsRecording = false;
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    private void initAudioRecorder() throws IOException {

        int sampleRateInHz = 8000;
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;


        this.mBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, DEFAULT_AUDIO_FORMAT.getAudioFormat());
        int bytesPerFrame = DEFAULT_AUDIO_FORMAT.getBytesPerFrame();
        int frameSize = this.mBufferSize / bytesPerFrame;
        if(frameSize % 160 != 0) {
            frameSize += 160 - frameSize % 160;
            this.mBufferSize = frameSize * bytesPerFrame;
        }

//        this.mAudioRecord = new AudioRecord(1, '걄', 16, DEFAULT_AUDIO_FORMAT.getAudioFormat(), this.mBufferSize);



        this.mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRateInHz,
                channelConfig,
                DEFAULT_AUDIO_FORMAT.getAudioFormat(), this.mBufferSize);

        this.mPCMBuffer = new short[this.mBufferSize];
//        LameUtil.init('걄', 1, '걄', 32, 7);
        LameUtil.init(sampleRateInHz, 1, sampleRateInHz, 32, 7);
        this.mEncodeThread = new DataEncodeThread(this.mRecordFile, this.mBufferSize);
        this.mEncodeThread.start();
        this.mAudioRecord.setRecordPositionUpdateListener(this.mEncodeThread, this.mEncodeThread.getHandler());
        this.mAudioRecord.setPositionNotificationPeriod(160);
    }

    static {
        DEFAULT_AUDIO_FORMAT = PCMFormat.PCM_16BIT;
    }


}
