package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.presentation.common.IndicatorState;

import javax.inject.Inject;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class NoveltySwipeController extends ItemTouchHelper.Callback {

    private int swipeWidth;
    private int swipeWidthThreshold;
    private boolean swipeBack;
    private IndicatorState indicatorShowedState;

    @Inject
    Resources resources;

    NoveltySwipeController() {
        super();
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        swipeWidth = (int) resources.getDimension(R.dimen.recycler_swipe_button_width);
        swipeWidthThreshold = (int) resources.getDimension(R.dimen.recycler_swipe_treshold);
        // TODO: 2/26/2019 resources bean
        if (actionState == ACTION_STATE_SWIPE)
            setTouchListener(canvas, recyclerView, viewHolder, dX, dY, isCurrentlyActive);
        handleSwipeDistances(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void handleSwipeDistances(@NonNull Canvas canvas,
                                      @NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      float dX, float dY,
                                      int actionState, boolean isCurrentlyActive) {
        if (dX < -swipeWidth - swipeWidthThreshold)
            dX = -swipeWidth - swipeWidthThreshold;
        if (dX > swipeWidth + swipeWidthThreshold)
            dX = swipeWidth + swipeWidthThreshold;
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchAntiGlitch(final @NonNull Canvas canvas,
                                    final @NonNull RecyclerView recyclerView,
                                    final @NonNull RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState,
                                    final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                NoveltySwipeController.super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((v1, event1) -> false);
                setRecyclerItemsClickable(recyclerView, true);
                swipeBack = false;
                indicatorShowedState = IndicatorState.GONE;
            }
            return false;
        });
    }

    private void setRecyclerItemsClickable(@NonNull RecyclerView recyclerView,
                                           boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder holder,
                                  float dX, float dY,
                                  boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
            if (swipeBack) {
                indicatorShowedState = IndicatorState.GONE;
                if (dX < -swipeWidth)
                    indicatorShowedState = IndicatorState.RIGHT_VISIBLE;
                else if (dX > swipeWidth)
                    indicatorShowedState = IndicatorState.LEFT_VISIBLE;
                if (indicatorShowedState == IndicatorState.GONE) {
                    setTouchAntiGlitch(c, recyclerView, holder, dX, dY, ACTION_STATE_SWIPE, isCurrentlyActive);
                    setRecyclerItemsClickable(recyclerView, false);
                }
            }
            return false;
        });
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                         int direction) {
    }
}
