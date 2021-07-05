package com.example.navernews.Model;

import com.example.navernews.InterFaces.MainContract;

public class NewsModel {
    private MainContract.Presenter presenter;

    public NewsModel(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
