package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import android.adservices.topics.Topic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.Category;
import com.example.cs4514_jlpt_exam_helper.api.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.api.repository.LearningItemRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class LearningDashboardViewModel extends ViewModel {
    private LearningItemRepository repository;
    private MutableLiveData<List<Category>> topicsLiveData;

    private static final List<Category> DEFAULT_TOPICS = new ArrayList<Category>() {{
        add(new Category("Hiragana & Katakana", R.drawable.image_hiragana, 0, "Beginner"));
        add(new Category("Vocabulary", R.drawable.image_vocabulary, 0, "Beginner"));
        add(new Category("Grammar", R.drawable.image_grammar, 0, "Beginner"));
        add(new Category("Listening", R.drawable.image_listening, 0, "Beginner"));
    }};

    public LearningDashboardViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public void getUserProgress(String level, String sessionToken) {
        Single<ResponseBean<Category>> response = repository.
                getUserProgress(level, sessionToken);
        response.subscribe(new SingleObserver<ResponseBean<Category>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<Category> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <= 299) {

                } else {

                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }


}
