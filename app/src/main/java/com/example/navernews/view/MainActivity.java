package com.example.navernews.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.R;
import com.example.navernews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private ActivityMainBinding binding;
    private NewsPresenter mainPresenter;
    private NewsRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainPresenter = new NewsPresenter(this);

        rvAdapter = new NewsRVAdapter(mainPresenter);
        binding.newsRv.setAdapter(rvAdapter);
    }

    @Override
    public void onDataChange() {
        rvAdapter.notifyDataSetChanged();
    }
}