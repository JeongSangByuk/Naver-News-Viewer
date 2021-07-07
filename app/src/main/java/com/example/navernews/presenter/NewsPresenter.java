package com.example.navernews.presenter;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.model.NewsDTO;
import com.example.navernews.model.NewsModel;
import com.example.navernews.utils.Constants;
import com.example.navernews.view.NewsRVAdapter;

import java.util.ArrayList;

public class NewsPresenter implements MainContract.Presenter {

    private MainContract.MainView mainView;
    private ArrayList<NewsDTO> news;
    private NewsModel newsModel;
    public String nowCategory;

    public NewsPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
        newsModel = new NewsModel(this);
        nowCategory = Constants.NOW_CATEGORY.POL.toString();
        setNews();
    }

    @Override
    public int getNewsCount() {
        return news.size();
    }

    @Override
    public void onBindNewsItem(int position, NewsRVAdapter.NewsRVViewHolder holder) {
        holder.setItem(news.get(position));
    }

    public void setNews() {
        this.news = newsModel.getNewsData();
    }

    public ArrayList<NewsDTO> getNews() {
        return news;
    }

    public MainContract.MainView getMainView() {
        return mainView;
    }
}
