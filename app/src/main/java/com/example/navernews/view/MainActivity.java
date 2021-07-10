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

    private Context context;
    private ActivityMainBinding binding;
    private NewsPresenter mainPresenter;
    private NewsRVAdapter rvAdapter;
    private LoadingDialog loadingDialog;
    private long clickedTime;

    private Swipe swipe;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        setStatusBar();

        mainPresenter = new NewsPresenter(this);
        setCategoryView();

        clickedTime = 0;
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

        SwipeHandlerCallback swipeHandlerCallback = new SwipeHandlerCallback(getResources());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHandlerCallback);
        itemTouchHelper.attachToRecyclerView(binding.newsRv);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;
        Log.d("qwe", String.valueOf(width));
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
        clickedTime = System.currentTimeMillis();
        binding.newsRv.stopScroll();
        rvAdapter = new NewsRVAdapter(mainPresenter);
        binding.newsRv.setAdapter(rvAdapter);

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

    @Override
    public void connectLink(String url) {

        // 더블클릭 방지.
//        if (System.currentTimeMillis() <= clickedTime + 500)
//            return;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
        Log.d("qwe", "click");
    }

}