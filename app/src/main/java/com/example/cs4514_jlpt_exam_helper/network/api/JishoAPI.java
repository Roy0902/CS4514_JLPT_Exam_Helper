package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.dictionary.JishoResponse;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JishoAPI {
    @GET("/jisho/search-dictionary")
    Single<ResponseBean<List<JishoResponse>>> searchWord(@Query("keyword") String keyword);
}
