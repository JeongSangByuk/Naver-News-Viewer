package com.example.navernews.model;

import com.example.navernews.interFaces.MainContract;

public class NewsModel {
    private MainContract.Presenter presenter;

    public NewsModel(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
