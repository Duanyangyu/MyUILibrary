package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.voice.AudioRecorderButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class VoiceActivity extends AppCompatActivity implements AudioRecorderButton.RecorderFinishListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        AudioRecorderButton recorderButton = (AudioRecorderButton) findViewById(R.id.audio_recorder_button);
        recorderButton.setRecordFinishListener(this);
        x.Ext.init(getApplication());
    }

    private static final String TAG = "VoiceActivity";
    @Override
    public void onRecordFinished(float mTime, String filePath) {

        Log.d(TAG,"VoiceActivity");

        RequestParams params = new RequestParams("http://192.168.0.46:8080/doctorfollowup/sendDialogueVoice.html");
        params.addBodyParameter("patientPhone","opxOxsxLXKoYW9bu_50mzzufV3Ts");
        params.addBodyParameter("fileContent",new File(filePath));
        x.http().post(params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG,"语音消息上传成功"+result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.d(TAG,"语音消息发送失败"+ex.getMessage()+ex.getCause().getMessage()+ex.getCause().toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.d(TAG,"语音消息发送取消");
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }


    public void btnPlay(View view) {

    }
}
