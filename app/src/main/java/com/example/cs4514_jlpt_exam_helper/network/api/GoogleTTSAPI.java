package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleTTSAPI {
    @GET("/google-tts/google-tts-service")
    Single<ResponseBean<String>> getGoogleTTSService(@Query("text") String text);
}
