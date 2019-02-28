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

    @SuppressLint("ClickableViewAccessibility")
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
            handleSwipeBack(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            button = drawer.drawButton(canvas, viewHolder.itemView, true);
            if (swipeWidth <= dX && dX <= swipeWidth + swipeWidthThreshold) {
                actions.onLeftClicked(viewHolder.getAdapterPosition());
                button = drawer.drawButton(canvas, viewHolder.itemView, false);
            }

        }
        handleSwipeDistances(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleSwipeBack(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            swipeBack = event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL;
            if (swipeBack)
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return false;
        });
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
