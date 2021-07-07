package com.example.navernews.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.model.NewsDTO;
import com.example.navernews.R;
import com.example.navernews.databinding.ItemNewsBinding;
import com.example.navernews.presenter.NewsPresenter;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsRVViewHolder> {

    private NewsPresenter newsPresenter;

    public NewsRVAdapter(NewsPresenter newsPresenter) {
        this.newsPresenter = newsPresenter;
    }

    @NonNull
    @Override
    public NewsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.NewsRVViewHolder holder, int position) {
        // presenter에 위임
        newsPresenter.onBindNewsItem(position,holder);
    }

    @Override
    public int getItemCount() {
        return newsPresenter.getNewsCount();
    }

    public class NewsRVViewHolder extends RecyclerView.ViewHolder implements MainContract.NewsItemView {

        public ItemNewsBinding binding;

        public NewsRVViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        // View에서 UI 업데이트
        @Override
        public void setItem(NewsDTO newsDTO) {
            binding.setNews(newsDTO);
        }
    }
}
