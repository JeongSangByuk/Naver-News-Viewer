package com.example.navernews.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navernews.interfaces.MainContract;
import com.example.navernews.model.NewsDTO;
import com.example.navernews.model.NewsModel;
import com.example.navernews.utils.Constants;
import com.example.navernews.view.NewsRVAdapter;

import java.util.ArrayList;

public class NewsPresenter implements MainContract.Presenter {

    private MainContract.MainView mainView;
    private ArrayList<NewsDTO> news;
    private NewsModel newsModel;
    public Constants.NOW_CATEGORY nowCategory;

    private boolean isLoading;
    private int nowPageNum;
    private int lastVisibleItem, totalItemCount;
    public boolean isUpdating;

    public NewsPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
        newsModel = new NewsModel(this);
        nowCategory = Constants.NOW_CATEGORY.POL;

        initPageNum();
        setNews();
    }

    @Override
    public int getNewsCount() {
        return news.size();
    }

    @Override
    public void onBindNewsItem(int position, NewsRVAdapter.NewsRVViewHolder holder) {
        holder.setNewsItem(news.get(position));
    }

    @Override
    public void onScroll(LinearLayoutManager linearLayoutManager) {
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!isLoading && totalItemCount <= (lastVisibleItem)) {
            isLoading = true;
            onLoadMore();
        }
    }

    @Override
    public void onLoadMore() {
        Log.d("qwe", "add");
        nowPageNum++;
        newsModel.addNewsData(nowPageNum);
    }

    @Override
    public void onLoaded() {
        isLoading = false;
        totalItemCount = Constants.MAX_NEWS_COUNT * nowPageNum - 1;
    }

    @Override
    public void setCategory(Constants.NOW_CATEGORY category) {

        if(isUpdating)
            return;

        initPageNum();

        nowCategory = category;
        mainView.setCategoryView();
        setNews();
    }

    @Override
    public void setCategory(int category) {
        nowCategory = Constants.NOW_CATEGORY.values()[category];
        mainView.setCategoryView();
        setNews();
    }

    public void setNews() {
        initPageNum();
        this.news = newsModel.getNewsData();
    }

    public ArrayList<NewsDTO> getNews() {
        return news;
    }

    public MainContract.MainView getMainView() {
        return mainView;
    }

    public void initPageNum(){
        nowPageNum = 1;
        totalItemCount = Constants.MAX_NEWS_COUNT - 1;
        isLoading = false;
        isUpdating = false;
    }

}
