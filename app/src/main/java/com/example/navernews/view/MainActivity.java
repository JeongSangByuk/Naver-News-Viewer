package com.example.navernews.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.navernews.interfaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.R;
import com.example.navernews.databinding.ActivityMainBinding;
import com.example.navernews.utils.Constants;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private ActivityMainBinding binding;
    private NewsPresenter mainPresenter;
    private NewsRVAdapter rvAdapter;
    private LoadingDialog loadingDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        setStatusBar();

        mainPresenter = new NewsPresenter(this);
        setCategoryView();

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void setCategoryView() {

        //view 초기화
        binding.vPol.getLayoutParams().height = changeDpToPx(1);
        binding.vEco.getLayoutParams().height = changeDpToPx(1);
        binding.vSoc.getLayoutParams().height = changeDpToPx(1);
        binding.vLif.getLayoutParams().height = changeDpToPx(1);
        binding.vIt.getLayoutParams().height = changeDpToPx(1);

        switch (mainPresenter.nowCategory){
            case POL:
                binding.vPol.getLayoutParams().height = changeDpToPx(3);
                break;
            case ECO:
                binding.vEco.getLayoutParams().height = changeDpToPx(3);
                break;
            case SOC:
                binding.vSoc.getLayoutParams().height = changeDpToPx(3);
                break;
            case LIF:
                binding.vLif.getLayoutParams().height = changeDpToPx(3);
                break;
            case IT:
                binding.vIt.getLayoutParams().height = changeDpToPx(3);
                break;
        }
        binding.vEco.requestLayout();
    }

    public void onClickPolTv(View view) {
        onClickCategoryView(Constants.NOW_CATEGORY.POL);
    }

    public void onClickEcoTv(View view) {
        onClickCategoryView(Constants.NOW_CATEGORY.ECO);
    }

    public void onClickSocTv(View view) {
        onClickCategoryView(Constants.NOW_CATEGORY.SOC);
    }

    public void onClickLifTv(View view) {
        onClickCategoryView(Constants.NOW_CATEGORY.LIF);
    }

    public void onClickItTv(View view) {
        onClickCategoryView(Constants.NOW_CATEGORY.IT);
    }

    @Override
    public void onClickCategoryView(Constants.NOW_CATEGORY category){

        if(mainPresenter.nowCategory == category)
            return;
        mainPresenter.setCategory(category);
    }

    public int changeDpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // 상태바 색 바꾸기
    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBar() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));//색 지정
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingdialog() {
        loadingDialog.dismiss();
    }

}