package com.example.navernews.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.R;
import com.example.navernews.databinding.ActivityMainBinding;
import com.example.navernews.utils.Constants;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private ActivityMainBinding binding;
    private NewsPresenter mainPresenter;
    private NewsRVAdapter rvAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setStatusBar();

        mainPresenter = new NewsPresenter(this);

        rvAdapter = new NewsRVAdapter(mainPresenter);
        binding.newsRv.setAdapter(rvAdapter);
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.setNews();
            }
        });
    }

    @Override
    public void onDataChange() {
        rvAdapter.notifyDataSetChanged();
        binding.swipeLayout.setRefreshing(false);
    }

    @Override
    public void setCategoryView() {
        switch (mainPresenter.nowCategory){
            case Constants.NOW_CATEGORY.POL.toString():
                mainPresenter.nowCategory = // 여기 now카테고리 get set 만들기부터,,
                break;


        }
    }

    // 상태바 색 바꾸기
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));//색 지정
    }

}