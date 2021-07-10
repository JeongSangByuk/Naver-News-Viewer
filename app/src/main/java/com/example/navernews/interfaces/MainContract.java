package com.example.navernews.interfaces;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.navernews.model.NewsDTO;
import com.example.navernews.utils.Constants;
import com.example.navernews.view.NewsRVAdapter;

public interface MainContract {

    interface MainView{
        void onDataChange(boolean isAdded);
        void onDataRefresh(View view);
        void setCategoryView();
        void onClickCategoryView(Constants.NOW_CATEGORY category);
        void onClickStarIv(View view);
        void onClickBackIv(View view);
        void setStatusBar();
        void showLoadingDialog();
        void dismissLoadingdialog();
        void connectLink(String url);
    }

    interface NewsItemView{
        void setNewsItem(NewsDTO newsDTO);
        void onItemClick(String url);
    }

    interface Presenter<T>{

        int getNewsCount();
        void onBindNewsItem(int position, NewsRVAdapter.NewsRVViewHolder holder);
        void onScroll(LinearLayoutManager linearLayoutManager);
        void onLoadMore();
        void onLoaded();
        void setCategory(Constants.NOW_CATEGORY category);

    }

}
