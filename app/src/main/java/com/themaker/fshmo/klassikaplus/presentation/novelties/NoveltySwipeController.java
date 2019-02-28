package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.presentation.common.SideButtonsState;
import com.themaker.fshmo.klassikaplus.presentation.decoration.ButtonDrawer;
import com.themaker.fshmo.klassikaplus.presentation.decoration.SideButtonDrawer;

import javax.inject.Inject;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class NoveltySwipeController extends ItemTouchHelper.Callback {

    private final NoveltySwipeControllerActions actions;

    private int swipeWidth;
    private int swipeWidthThreshold;
    private boolean swipeBack;
    private SideButtonsState sideButtonsState = SideButtonsState.GONE;
    private RecyclerView.ViewHolder currentItemViewHolder;

    private ButtonDrawer drawer = new SideButtonDrawer();
    private RectF button;

    @Inject
    Resources resources;

    NoveltySwipeController(NoveltySwipeControllerActions actions) {
        super();
        this.actions = actions;
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, RIGHT);
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        swipeWidth = (int) resources.getDimension(R.dimen.recycler_swipe_button_width);
        swipeWidthThreshold = (int) resources.getDimension(R.dimen.recycler_swipe_threshold);
        currentItemViewHolder = viewHolder;

        if (actionState == ACTION_STATE_SWIPE) {
            setTouchListener(canvas, recyclerView, viewHolder, dX, dY, isCurrentlyActive);
            button = drawer.drawButton(canvas, viewHolder.itemView, true);
        }
        handleSwipeDistances(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void handleSwipeDistances(@NonNull Canvas canvas,
                                      @NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      float dX, float dY,
                                      int actionState, boolean isCurrentlyActive) {
        if (dX < -swipeWidth) {
            sideButtonsState = SideButtonsState.LEFT_VISIBLE;
            if (dX < -swipeWidth - swipeWidthThreshold)
                dX = -swipeWidth - swipeWidthThreshold;
        }
        if (dX > swipeWidth) {
            sideButtonsState = SideButtonsState.RIGHT_VISIBLE;
            if (dX > swipeWidth + swipeWidthThreshold)
                dX = swipeWidth + swipeWidthThreshold;
        }
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
                sideButtonsState = SideButtonsState.GONE;
                if (dX < -swipeWidth) sideButtonsState = SideButtonsState.RIGHT_VISIBLE;
                else if (dX > swipeWidth) sideButtonsState = SideButtonsState.LEFT_VISIBLE;
                if (sideButtonsState != SideButtonsState.GONE) {
                    setRecyclerItemsClickable(recyclerView, false);
                    setTouchDownListener(c, recyclerView, holder, dX, dY,
                            ACTION_STATE_SWIPE, isCurrentlyActive);
                }
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                NoveltySwipeController.super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((v1, event1) -> false);
                setRecyclerItemsClickable(recyclerView, true);
                swipeBack = false;

                if (sideButtonsState != null && button != null && button.contains(event.getX(), event.getY())) {
                    if (sideButtonsState == SideButtonsState.LEFT_VISIBLE) {
                        actions.onLeftClicked(viewHolder.getAdapterPosition());
                    } else if (sideButtonsState == SideButtonsState.RIGHT_VISIBLE) {
                        actions.onRightClicked(viewHolder.getAdapterPosition());
                    }
                }
                sideButtonsState = SideButtonsState.GONE;
                currentItemViewHolder = null;
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

    public void onDraw(Canvas canvas) {
        if (currentItemViewHolder != null)
            drawer.drawButton(canvas, currentItemViewHolder.itemView, false);
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }
}
