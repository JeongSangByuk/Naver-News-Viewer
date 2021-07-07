package com.example.navernews.interFaces;

import com.example.navernews.model.NewsDTO;
import com.example.navernews.view.NewsRVAdapter;

public interface MainContract {

    interface MainView{
        void onDataChange();
        void setCategoryView();
    }

    interface NewsItemView{
        void setItem(NewsDTO newsDTO);
    }

    interface Presenter{

        int getNewsCount();
        void onBindNewsItem(int position, NewsRVAdapter.NewsRVViewHolder holder);


    }

}
