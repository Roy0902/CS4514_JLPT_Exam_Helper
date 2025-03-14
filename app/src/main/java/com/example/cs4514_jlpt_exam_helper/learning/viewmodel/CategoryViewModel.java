package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.data.Category;

import java.util.ArrayList;


public class CategoryViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<ArrayList<Category>> categories = new  MutableLiveData<ArrayList<Category>>();

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

    public void setUpBeginnerCategory(){
        ArrayList<Category> BEGINNER_CATEGORIES = new ArrayList<Category>() {{
            add(new Category("Hiragana & Katakana", 0, "Beginner",
                    R.drawable.image_hiragana, R.drawable.background_item_hiragana));;
            add(new Category("Grammar", 0, "Beginner" ,
                    R.drawable.image_grammar, R.drawable.background_item_grammer));;
        }};
        categories.setValue(BEGINNER_CATEGORIES);
    }

}
