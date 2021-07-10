package com.example.navernews.utils;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;
import com.example.navernews.interfaces.MainContract;
import com.example.navernews.model.NewsDTO;
import com.example.navernews.view.MainActivity;
import com.example.navernews.view.NewsRVAdapter;

import org.jetbrains.annotations.NotNull;

public class SwipeHandlerCallback extends ItemTouchHelper.Callback {

    private MainActivity mainActivity;

    public SwipeHandlerCallback(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        NewsDTO newsDTO =  mainActivity.getMainPresenter().getNews().get(position);
        mainActivity.getMainPresenter().getArchiveNewsModel().insertArchiveNews(newsDTO);
        mainActivity.getMainPresenter().getNews().remove(position);
        mainActivity.getBinding().newsRv.getAdapter().notifyItemRemoved(position);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        getDefaultUIUtil().clearView(getView((NewsRVAdapter.NewsRVViewHolder) viewHolder));
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(getView((NewsRVAdapter.NewsRVViewHolder) viewHolder));
        }
    }

//    @Override
//    public float getSwipeThreshold(@NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
//        return 2f;
//    }
//
//    @Override
//    public float getSwipeEscapeVelocity(float defaultValue) {
//        return defaultValue*10;
//    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View view = getView((NewsRVAdapter.NewsRVViewHolder) viewHolder);
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
        }
    }

    

    private View getView(NewsRVAdapter.NewsRVViewHolder viewHolder) {
        return viewHolder.binding.viewMain;
    }

}
