package com.example.cs4514_jlpt_exam_helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.GoogleTTSRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class GoogleTTSManager {
    private GoogleTTSRepository repository;
    private static GoogleTTSManager manager;

    public GoogleTTSManager(){
        if(repository == null){
            repository = GoogleTTSRepository.getInstance();
        }
    }

    public static GoogleTTSManager getInstance(){
        if(manager == null){
            manager = new GoogleTTSManager();
        }

        return manager;
    }

    public void getTTSService(String text, MediaPlayer player, Context context){
        if(text == null || player == null){
            return;
        }


        Single<ResponseBean<String>> response = repository.
                getGoogleTTSService(text);
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    String base64Audio = bean.getData();
                    byte[] audioBytes = Base64.getDecoder().decode(base64Audio);
                    player.reset();
                    try {
                        File tempFile = File.createTempFile("tts", ".mp3", context.getCacheDir());
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        fos.write(audioBytes);
                        player.setDataSource(tempFile.getAbsolutePath());
                        player.prepare();
                        player.start();
                        player.setOnCompletionListener(mp -> tempFile.delete());
                    } catch (IOException e) {
                        Toast.makeText(context,"Failed to play the audio.", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
                d.dispose();
            }
        });
    }

    public void stopPlayer(MediaPlayer player) {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.reset();
        }
    }

}
