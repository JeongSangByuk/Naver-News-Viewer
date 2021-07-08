package com.example.navernews.interfaces;

import com.example.navernews.model.NewsDTO;
import com.example.navernews.utils.Constants;
import com.example.navernews.view.NewsRVAdapter;

public interface MainContract {

    interface MainView{
        void onDataChange();
        void setCategoryView();
        void onClickCategoryView(Constants.NOW_CATEGORY category);
        void setStatusBar();
        void showLoadingDialog();
        void dismissLoadingdialog();
        void connectLink(String url);
    }

    interface NewsItemView{
        void setNewsItem(NewsDTO newsDTO);
        void onItemClick(String url);
    }

    interface Presenter{

        int getNewsCount();
        void onBindNewsItem(int position, NewsRVAdapter.NewsRVViewHolder holder);
        void setCategory(Constants.NOW_CATEGORY category);

    }

}
