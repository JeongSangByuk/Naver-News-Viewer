package com.example.navernews.Presenter;

import com.example.navernews.InterFaces.MainContract;

public class NewsPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    public NewsPresenter(MainContract.View mainView) {
        this.mainView = mainView;
    }

    @Override
    public void changeData(String text1 , String text2) {
        mainView.setNewTvData(text1 + text2);
    }
}
