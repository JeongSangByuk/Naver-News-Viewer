package com.example.navernews.model;

import android.util.Log;

import com.example.navernews.presenter.NewsPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArchiveNewsModel {

    private NewsPresenter presenter;
    private Disposable backgroundtask;
    private List<NewsDTO> news;
    private ArrayList<NewsDTO> resultNews;

    private AppNewsDatabase newsDB;

    public ArchiveNewsModel(NewsPresenter presenter) {
        this.presenter = presenter;
        newsDB = AppNewsDatabase.getInstance(presenter.context);
    }

    public ArrayList<NewsDTO> getNewsArchiveData() {

        resultNews = new ArrayList<>();
        backgroundTask();

        return resultNews;
    }

    public void insertArchiveNews(NewsDTO news){
        backgroundInsertTask(news);
    }

    void backgroundTask() {

        //onPreExecute
        presenter.getMainView().showLoadingDialog();

        backgroundtask = Observable.fromCallable(() -> {

            //doInBackground

            news = newsDB.newsDAO().selectAll(presenter.nowCategory.toString());
            return false;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            //onPostExecute

            resultNews.addAll(news);
            presenter.getMainView().onDataChange(false);
            presenter.getMainView().dismissLoadingdialog();
            presenter.onLoaded();
            backgroundtask.dispose();
        });
    }

    void backgroundInsertTask(NewsDTO news) {

        //onPreExecute
        presenter.getMainView().showLoadingDialog();

        backgroundtask = Observable.fromCallable(() -> {

            //doInBackground
            newsDB.newsDAO().insert(news);;
            return false;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            //onPostExecute
            presenter.getMainView().dismissLoadingdialog();
            backgroundtask.dispose();
        });
    }


}
