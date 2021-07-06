package com.example.navernews.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navernews.model.NewsDTO;
import com.example.navernews.R;
import com.example.navernews.databinding.ItemNewsBinding;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsRVViewHolder> {


    @NonNull
    @Override
    public NewsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.NewsRVViewHolder holder, int position) {

        holder.binding.setNews(new NewsDTO("상상 그이상상상 그이상상상 그이상상상 그이상","상상 그 이상으로가는 상상","7월 7일 21:01"));
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class NewsRVViewHolder extends RecyclerView.ViewHolder{

        protected ItemNewsBinding binding;

        public NewsRVViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
