package com.example.navernews.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.navernews.interfaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.R;
import com.example.navernews.databinding.ActivityMainBinding;
import com.example.navernews.utils.Constants;
import com.example.navernews.utils.SwipeHandlerCallback;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;

import java.nio.channels.InterruptedByTimeoutException;

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

        mainPresenter = new NewsPresenter(this,getApplicationContext());
        setCategoryView();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.newsRv.getLayoutManager();

        rvAdapter = new NewsRVAdapter(mainPresenter);
        binding.newsRv.setAdapter(rvAdapter);

        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh(binding.getActivity().getWindow().getDecorView());
            }
        });

        binding.newsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mainPresenter.onScroll(linearLayoutManager);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHandlerCallback(this));
        itemTouchHelper.attachToRecyclerView(binding.newsRv);
    }

    @Override
    public void onDataChange(boolean isAdded) {
        rvAdapter.notifyDataSetChanged();
        binding.swipeLayout.setRefreshing(false);

        if (!isAdded)
            binding.newsRv.scrollToPosition(0);
    }

    @Override
    public void onDataRefresh(View view) {
        initRv();
        mainPresenter.setNews();
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

        switch (mainPresenter.nowCategory) {
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
    public void onClickCategoryView(Constants.NOW_CATEGORY category) {

        if (mainPresenter.nowCategory == category)
            return;

        initRv();

        mainPresenter.setCategory(category);
    }

    @Override
    public void onClickStarIv(View view) {


        binding.ivBack.setClickable(true);
        mainPresenter.setArchiveState(true);

        binding.tvTitle.setText("내 뉴스 아카이브");
        changeModeColor(R.color.mainBlue);

        binding.ivStar.setVisibility(View.INVISIBLE);
        binding.ivRefresh.setVisibility(View.INVISIBLE);

        initRv();

        mainPresenter.setCategory(mainPresenter.nowCategory);
    }

    @Override
    public void onClickBackIv(View view) {
        onBackPressed();
    }

    public int changeDpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void changeModeColor(int color){
        binding.tvTitle.setTextColor(getResources().getColor(color));

        binding.tvPol.setTextColor(getResources().getColor(color));
        binding.tvLif.setTextColor(getResources().getColor(color));
        binding.tvSoc.setTextColor(getResources().getColor(color));
        binding.tvEco.setTextColor(getResources().getColor(color));
        binding.tvIt.setTextColor(getResources().getColor(color));

        binding.vLif.setBackgroundColor(getResources().getColor(color));
        binding.vIt.setBackgroundColor(getResources().getColor(color));
        binding.vEco.setBackgroundColor(getResources().getColor(color));
        binding.vSoc.setBackgroundColor(getResources().getColor(color));
        binding.vPol.setBackgroundColor(getResources().getColor(color));

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

    @Override
    public void showInsertingToastMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),"내 뉴스 아카이브에 추가됐습니다.",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showDeletingToastMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),"내 뉴스 아카이브에서 삭제됐습니다.",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void connectLink(String url) {

        // 더블클릭 방지.
//        if (System.currentTimeMillis() <= clickedTime + 500)
//            return;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public ActivityMainBinding getBinding() {
        return binding;
    }

    public NewsPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void initRv(){
        binding.newsRv.stopScroll();
        rvAdapter = new NewsRVAdapter(mainPresenter);
        binding.newsRv.setAdapter(rvAdapter);
    }

    @Override
    public void onBackPressed() {
        if(!mainPresenter.isArchiveState())
            super.onBackPressed();
        else{
            binding.ivBack.setClickable(false);
            mainPresenter.setArchiveState(false);

            changeModeColor(R.color.mainGreen);
            binding.tvTitle.setText("네이버 최신 속보");
            changeModeColor(R.color.mainGreen);

            binding.ivStar.setVisibility(View.VISIBLE);
            binding.ivRefresh.setVisibility(View.VISIBLE);

            initRv();

            mainPresenter.setCategory(mainPresenter.nowCategory);
        }
    }
}