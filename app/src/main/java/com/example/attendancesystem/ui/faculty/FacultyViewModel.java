package com.example.attendancesystem.ui.faculty;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FacultyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FacultyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Faculty fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}