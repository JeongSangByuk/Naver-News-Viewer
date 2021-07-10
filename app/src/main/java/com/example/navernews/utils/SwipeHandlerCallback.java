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
import com.example.navernews.view.MainActivity;
import com.example.navernews.view.NewsRVAdapter;

public class SwipeHandlerCallback extends ItemTouchHelper.Callback {

    private Resources resource;
    private float downPosition,upPosition;

    public SwipeHandlerCallback(Resources resource) {
        this.resource = resource;
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

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View view = getView((NewsRVAdapter.NewsRVViewHolder) viewHolder);
            Log.d("qwe Dx", String.valueOf(dX));
            Log.d("qwe p",String.valueOf(getWidthValue(60)));
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX * getWidthValue(60), dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue * 10;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.1f;
    }

    private View getView(NewsRVAdapter.NewsRVViewHolder viewHolder) {
        return viewHolder.binding.viewMain;
    }

    public float getWidthValue(int dp) {
        float density = resource.getDisplayMetrics().density;

        float x = resource.getDisplayMetrics().widthPixels;

        return (Math.round((float) dp * density))/x;
    }
}
