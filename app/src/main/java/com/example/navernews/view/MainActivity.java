package com.example.navernews.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.R;
import com.example.navernews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter mainPresenter;
    private NewsRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainPresenter = new NewsPresenter(this);

        initListener();
        rvAdapter = new NewsRVAdapter();
        binding.newsRv.setAdapter(rvAdapter);
    }


    private void initListener(){


    }

    private void getTextData(){


    }

    @Override
    public void setNewTvData(String text) {

    }
}