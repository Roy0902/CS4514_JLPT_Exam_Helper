package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.network.response.CategoryProgressResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


public class CategoryViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<ArrayList<Category>> categories = new  MutableLiveData<ArrayList<Category>>();
    private MutableLiveData<Boolean> isReady = new  MutableLiveData<Boolean>(false);

    private int count = 0;

    public CategoryViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<ArrayList<Category>> getCategories() {
        return categories;
    }

    public void setCategories(MutableLiveData<ArrayList<Category>> categories) {
        this.categories = categories;
    }

    public MutableLiveData<Boolean> getIsReady() {
        return isReady;
    }

    public void setIsReady(MutableLiveData<Boolean> isReady) {
        this.isReady = isReady;
    }

    public void getUserProgress(String level_name, Context context){
        if(level_name == null){
            return;
        }

        Single<ResponseBean<ArrayList<CategoryProgressResponse>>> response = repository.
                getCategoryProgress(context, level_name);
        response.subscribe(new SingleObserver<ResponseBean<ArrayList<CategoryProgressResponse>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<ArrayList<CategoryProgressResponse>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    ArrayList<CategoryProgressResponse> tempList = bean.getData();
                    ArrayList<Category> categoryList = new ArrayList<Category>();
                    for(CategoryProgressResponse c: tempList){
                        Category category = new Category(c.getCategory_name(),
                                                level_name,
                                                c.getCompleted_subtopics(),
                                                c.getTotal_subtopics());

                        if(c.getCategory_name().equals("Hiragana & Katakana")){
                            category.setImageResId(R.drawable.image_hiragana);
                            category.setColorResId(R.drawable.background_item_hiragana);
                        }else if(c.getCategory_name().equals("Grammar")){
                            category.setImageResId(R.drawable.image_grammar);
                            category.setColorResId(R.drawable.background_item_grammar);
                        }else if(c.getCategory_name().equals("Vocabulary")){
                            category.setImageResId(R.drawable.image_vocabulary);
                            category.setColorResId(R.drawable.background_item_vocabulary);
                        }

                        categoryList.add(category);
                    }

                    categories.setValue(categoryList);
                    isReady.setValue(true);
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
