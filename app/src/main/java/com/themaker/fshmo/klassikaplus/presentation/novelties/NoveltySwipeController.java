package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.themaker.fshmo.klassikaplus.R;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;
import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

public class NoveltySwipeController extends ItemTouchHelper.Callback {


    private int swipeWidth;
    private boolean swipeBack;

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        swipeWidth = (int) recyclerView.getResources().getDimension(R.dimen.recycler_swipe_button_width);
        if (actionState == ACTION_STATE_SWIPE)
            setTouchListener(c, recyclerView, viewHolder, dX, dX, isCurrentlyActive);
        if (dX < -swipeWidth - 20)
            dX = -swipeWidth - 20;
        if (dX > swipeWidth + 20)
            dX = swipeWidth + 20;
        // TODO: 2/26/2019 resources bean
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder holder,
                                  float dX, float dY,
                                  boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
            if (swipeBack) {
                IndicatorState indicatorShowedState = IndicatorState.GONE;
                if (dX < -swipeWidth)
                    indicatorShowedState = IndicatorState.RIGHT_VISIBLE;
                else if (dX > swipeWidth)
                    indicatorShowedState = IndicatorState.LEFT_VISIBLE;
                if (indicatorShowedState == IndicatorState.GONE) {
//                    setTouchDownListener
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
