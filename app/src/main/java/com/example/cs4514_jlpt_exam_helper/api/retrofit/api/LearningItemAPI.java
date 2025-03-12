package com.example.cs4514_jlpt_exam_helper.api.retrofit.api;

import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.data.Category;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LearningItemAPI {
    @POST("/get-user-progress")
    Single<ResponseBean<Category>> getUserProgressByLevel(@Body String level, String sessionToken);

}
