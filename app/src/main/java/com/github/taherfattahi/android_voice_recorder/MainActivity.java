package com.github.taherfattahi.android_voice_recorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

/**
 * @author taher fattahi
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO = 0;
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/recorded_audio.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        }

        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO, 1);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordAudio(View v) {

        if(checkIfAlreadyhavePermission(Manifest.permission.RECORD_AUDIO) &&
                checkIfAlreadyhavePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            AndroidAudioRecorder.with(this)
                    // Required
                    .setFilePath(AUDIO_FILE_PATH)
                    .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                    .setRequestCode(REQUEST_RECORD_AUDIO)

                    // Optional
                    .setSource(AudioSource.MIC)
                    .setChannel(AudioChannel.STEREO)
                    .setSampleRate(AudioSampleRate.HZ_48000)
                    .setAutoStart(false)
                    .setKeepDisplayOn(true)

                    // Start recording
                    .record();
        } else {
            Util.requestPermission(this, Manifest.permission.RECORD_AUDIO, 1);
            Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 2);
        }
    }

    private boolean checkIfAlreadyhavePermission(String typePermission) {
        int result = ContextCompat.checkSelfPermission(this, typePermission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(MainActivity.this, "accept Record Audio permission", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your Record Audio", Toast.LENGTH_SHORT).show();
                }

                Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 2);

                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Toast.makeText(MainActivity.this, "accept Write External Storage permission ", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External Storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}