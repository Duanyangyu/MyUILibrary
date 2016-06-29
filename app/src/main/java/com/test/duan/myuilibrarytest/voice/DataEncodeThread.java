package com.test.duan.myuilibrarytest.voice;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.moor.imkf.mp3recorder.util.LameUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Duanyy on 2016/6/24.
 */
public class DataEncodeThread  extends Thread implements AudioRecord.OnRecordPositionUpdateListener {

    public static final int PROCESS_STOP = 1;
    private DataEncodeThread.StopHandler mHandler;
    private byte[] mMp3Buffer;
    private FileOutputStream mFileOutputStream;
    private CountDownLatch mHandlerInitLatch = new CountDownLatch(1);
    private List<Task> mTasks = Collections.synchronizedList(new ArrayList());

    public DataEncodeThread(File file, int bufferSize) throws FileNotFoundException {
        this.mFileOutputStream = new FileOutputStream(file);
        this.mMp3Buffer = new byte[(int)(7200.0D + (double)(bufferSize * 2) * 1.25D)];
    }

    public void run() {
        Looper.prepare();
        this.mHandler = new DataEncodeThread.StopHandler(this);
        this.mHandlerInitLatch.countDown();
        Looper.loop();
    }

    public Handler getHandler() {
        try {
            this.mHandlerInitLatch.await();
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

        return this.mHandler;
    }

    public void onMarkerReached(AudioRecord recorder) {
    }

    public void onPeriodicNotification(AudioRecord recorder) {
        this.processData();
    }

    private int processData() {
        if(this.mTasks.size() > 0) {
            DataEncodeThread.Task task = (DataEncodeThread.Task)this.mTasks.remove(0);
            short[] buffer = task.getData();
            int readSize = task.getReadSize();
            int encodedSize = LameUtil.encode(buffer, buffer, readSize, this.mMp3Buffer);
            if(encodedSize > 0) {
                try {
                    this.mFileOutputStream.write(this.mMp3Buffer, 0, encodedSize);
                } catch (IOException var6) {
                    var6.printStackTrace();
                }
            }

            return readSize;
        } else {
            return 0;
        }
    }

    private void flushAndRelease() {
        int flushResult = LameUtil.flush(this.mMp3Buffer);
        if(flushResult > 0) {
            try {
                this.mFileOutputStream.write(this.mMp3Buffer, 0, flushResult);
            } catch (IOException var11) {
                var11.printStackTrace();
            } finally {
                if(this.mFileOutputStream != null) {
                    try {
                        this.mFileOutputStream.close();
                    } catch (IOException var10) {
                        var10.printStackTrace();
                    }
                }

                LameUtil.close();
            }
        }

    }

    public void addTask(short[] rawData, int readSize) {
        this.mTasks.add(new DataEncodeThread.Task(rawData, readSize));
    }

    private class Task {
        private short[] rawData;
        private int readSize;

        public Task(short[] rawData, int readSize) {
            this.rawData = (short[])rawData.clone();
            this.readSize = readSize;
        }

        public short[] getData() {
            return this.rawData;
        }

        public int getReadSize() {
            return this.readSize;
        }
    }

    static class StopHandler extends Handler {
        WeakReference<DataEncodeThread> encodeThread;

        public StopHandler(DataEncodeThread encodeThread) {
            this.encodeThread = new WeakReference(encodeThread);
        }

        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                DataEncodeThread threadRef = (DataEncodeThread)this.encodeThread.get();

                while(true) {
                    if(threadRef.processData() <= 0) {
                        this.removeCallbacksAndMessages((Object)null);
                        threadRef.flushAndRelease();
                        this.getLooper().quit();
                        break;
                    }
                }
            }

            super.handleMessage(msg);
        }
    }


}
