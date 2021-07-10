package com.example.navernews.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navernews.interfaces.MainContract;
import com.example.navernews.model.ArchiveNewsModel;
import com.example.navernews.model.NewsDTO;
import com.example.navernews.model.NewsModel;
import com.example.navernews.utils.Constants;
import com.example.navernews.view.NewsRVAdapter;

import java.util.ArrayList;

public class NewsPresenter implements MainContract.Presenter {

    private MainContract.MainView mainView;
    private ArrayList<NewsDTO> news;
    private NewsModel newsModel;
    private ArchiveNewsModel archiveNewsModel;
    public Constants.NOW_CATEGORY nowCategory;
    public Context context;

    private boolean isLoading;
    private boolean isArchiveState;
    private int nowPageNum;
    private int lastVisibleItem, totalItemCount;

    public NewsPresenter(MainContract.MainView mainView,Context context) {
        this.mainView = mainView;
        this.context = context;
        newsModel = new NewsModel(this);
        archiveNewsModel = new ArchiveNewsModel(this);
        nowCategory = Constants.NOW_CATEGORY.POL;
        isArchiveState = false;

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

        if(isArchiveState)
            return;

        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!isLoading && totalItemCount <= (lastVisibleItem)) {
            isLoading = true;
            onLoadMore();
        }
    }

    @Override
    public void onLoadMore() {
        nowPageNum++;
        newsModel.addNewsData(nowPageNum);
    }

    @Override
    public void onLoaded() {
        isLoading = false;
        totalItemCount = totalItemCount + Constants.MAX_NEWS_COUNT  - 1;
    }

    @Override
    public void setCategory(Constants.NOW_CATEGORY category) {

        if(isLoading)
            return;

        initPageNum();

        nowCategory = category;
        mainView.setCategoryView();

        if(!isArchiveState)
            setNews();
        else
            setArchiveNews();
    }

    public void setNews() {

        if(isLoading)
            return;

        initPageNum();
        isLoading = true;
        this.news = newsModel.getNewsData();
    }

    public void setArchiveNews(){
        initPageNum();
        isLoading = true;
        this.news = archiveNewsModel.getNewsArchiveData();
    }

    public ArrayList<NewsDTO> getNews() {
        return news;
    }

    public MainContract.MainView getMainView() {
        return mainView;
    }

    public void initPageNum(){
        nowPageNum = 1;
        totalItemCount = 0;
        isLoading = false;
    }

    public void minusTotalItemCount(){
        totalItemCount--;
    }

    public boolean isArchiveState() {
        return isArchiveState;
    }

    public void setArchiveState(boolean archiveState) {
        isArchiveState = archiveState;
    }

    public ArchiveNewsModel getArchiveNewsModel() {
        return archiveNewsModel;
    }
}
