/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.test.duan.myuilibrarytest.voice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.TextUtils;


import java.io.File;

public class MediaPlayTools {

	private static final String TAG = "MediaPlayTools";

	private static MediaPlayTools mInstance = null;

	/**
	 * The definition of the state of play 
	 * Play error
	 */
	private static final int STATUS_ERROR 				= -1;

	/**
	 * Stop playing
	 */
	private static final int STATUS_STOP 				= 0;

	/**
	 * Voice playing
	 */
	private static final int STATUS_PLAYING 			= 1;

	/**
	 * Pause playback
	 */
	private static final int STATUS_PAUSE 				= 2;

	private MediaPlayer mediaPlayer = new MediaPlayer();
	private OnVoicePlayCompletionListener mListener;

	private String urlPath = "";

	private int status = 0;

	public MediaPlayTools() {

		setOnCompletionListener();
		setOnErrorListener();
	}

	synchronized public static MediaPlayTools getInstance() {
		if (null == mInstance) {
			mInstance = new MediaPlayTools();
		}
		return mInstance;
	}

	/**
	 */
	private void play(boolean isEarpiece , int seek) {

		int streamType = AudioManager.STREAM_MUSIC;
		if(TextUtils.isEmpty(urlPath) || !new File(urlPath).exists()) {
			return ;
		}

		if(isEarpiece) {
			streamType = AudioManager.STREAM_VOICE_CALL;
		}

		if(mediaPlayer == null ) {
			mediaPlayer = new MediaPlayer();
			setOnCompletionListener();
			setOnErrorListener();
		}
		try {
			mediaPlayer.reset();
			mediaPlayer.setAudioStreamType(streamType);
			mediaPlayer.setDataSource(urlPath);
			mediaPlayer.prepare();
			if(seek > 0) {
				mediaPlayer.seekTo(seek);
			}
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private boolean play(String urlPath , boolean isEarpiece , int seek) {

		if(status != STATUS_STOP) {
			return false;
		}

		this.urlPath = urlPath;

		boolean result = false;
		try {
			play(isEarpiece, seek);
			this.status = STATUS_PLAYING;
			result = true;
		} catch (Exception e) {
			e.printStackTrace();

			try {
				play(true, seek);
				result = true;
			} catch (Exception e1) {
				e1.printStackTrace();
				result = false;
			}

		}

		return result;

	}

	/*
	 *
	 */
	public boolean playVoice(String urlPath , boolean isEarpiece) {

		return play(urlPath, isEarpiece, 0);
	}

	/**
	 *
	 */
	public boolean resume() {

		if( this.status != STATUS_PAUSE) {

			return false;
		}

		boolean result = false;

		try {
			mediaPlayer.start();
			this.status = STATUS_PLAYING ;
			result = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			this.status = STATUS_ERROR;
			result = false;
		}

		return result;

	}

	/**
	 *
	 */
	public boolean stop () {

		if(status != STATUS_PLAYING && status != STATUS_PAUSE)  {

			return false;
		}

		boolean result = false;
		try {
			if(mediaPlayer != null) {
				this.mediaPlayer.stop();
				this.mediaPlayer.release();
				this.mediaPlayer = null;
			}
			this.status = STATUS_STOP;
			result = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			this.status = STATUS_ERROR;
			result = false;

		}

		return result;
	}


	private boolean calling = false;


	/**
	 *
	 * @param speakerOn
	 */
	public void setSpeakerOn(boolean speakerOn) {


		if(mediaPlayer == null ) {
			mediaPlayer = new MediaPlayer();
		}

		if(calling) {
			// 这里需要判断当前的状态是否是正在系统电话振铃或者接听中

		} else {
			int currentPosition = mediaPlayer.getCurrentPosition();

			stop();

			setOnCompletionListener();
			setOnErrorListener();

			play(urlPath, !speakerOn, currentPosition);
		}
	}


	public boolean pause () {
		if(this.status != STATUS_PLAYING) {
			return false;
		}

		boolean result = false;

		try {

			mediaPlayer.pause();
			this.status = STATUS_PAUSE;
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			status = STATUS_ERROR;
		}

		return result;
	}


	public int getStatus() {
		return status;
	}

	public boolean isPlaying () {

		if(this.status == STATUS_PLAYING) {
			return true;
		}

		return false;
	}

	private void setOnCompletionListener() {

		// 
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				status = STATUS_STOP;

				if(mListener != null) {
					mListener.OnVoicePlayCompletion();
				}
			}
		});
	}

	private void setOnErrorListener() {

		//
		mediaPlayer.setOnErrorListener(null);
	}

	public void setOnVoicePlayCompletionListener(OnVoicePlayCompletionListener l) {
		mListener = l;
	}

	public interface OnVoicePlayCompletionListener {
		void OnVoicePlayCompletion();
	}
}
