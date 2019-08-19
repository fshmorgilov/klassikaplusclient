package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.presentation.common.SideButtonsState;
import com.themaker.fshmo.klassikaplus.presentation.decoration.SideDrawer;
import com.themaker.fshmo.klassikaplus.presentation.decoration.StarDrawer;

import javax.inject.Inject;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class NoveltySwipeController extends ItemTouchHelper.Callback {

    private static final String TAG = NoveltySwipeController.class.getName();
    private final NoveltySwipeControllerActions actions;

    private int swipeWidth;
    private int swipeWidthThreshold;
    private int swipeActionWidth;
    private boolean swipeBack;
    private SideButtonsState sideButtonsState = SideButtonsState.GONE;
    private RecyclerView.ViewHolder currentItemViewHolder;

    private SideDrawer drawer = new StarDrawer();
    private Item item;

    @Inject
    Resources resources;

    // FIXME: 3/2/2019 Звезды видны пока скроллишь между vieholder
    // FIXME: 3/2/2019 swipeback когда уже отмечено как favorite
    NoveltySwipeController(NoveltySwipeControllerActions actions) {
        super();
        this.actions = actions;
        App.getInstance().getComponent().inject(this);
        initializeResources();
    }

    public void onDraw(Canvas canvas) {
        if (currentItemViewHolder != null)
            drawer.draw(canvas, currentItemViewHolder.itemView, false);
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
        item = ((NoveltyViewHolder) viewHolder).getItem();
        currentItemViewHolder = viewHolder;
        if (actionState == ACTION_STATE_SWIPE) {
            handleSwipeBack(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            handleSwipeDistances(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
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
        if (dX <= -swipeWidth - swipeWidthThreshold)
            dX = -swipeWidth - swipeWidthThreshold;
        if (dX >= swipeWidth + swipeWidthThreshold)
            dX = swipeWidth + swipeWidthThreshold;
        handleCallback(canvas, viewHolder, dX);
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void handleCallback(@NonNull Canvas canvas, @NonNull RecyclerView.ViewHolder viewHolder, float dX) {
        if (item.isFavorite())
            drawer.draw(canvas, viewHolder.itemView, true);
        else {
            if (swipeWidth <= dX) {
                Log.d(TAG, "onChildDraw: current dX is :" + dX);
                if (dX == swipeWidth + swipeWidthThreshold && !item.isFavorite()) {
                    drawer.draw(canvas, viewHolder.itemView, true);
                    actions.onLeftSwiped(viewHolder.getLayoutPosition());
                    Log.d(TAG, "onChildDraw: callback triggered");
                }
            }
        }
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

    private void initializeResources() {
        swipeWidth = (int) resources.getDimension(R.dimen.recycler_swipe_button_width);
        swipeActionWidth = (int) resources.getDimension(R.dimen.recycler_swipe_action_width);
        swipeWidthThreshold = (int) resources.getDimension(R.dimen.recycler_swipe_threshold);
        Log.d(TAG, "initializeResources: swipeWidth: " + swipeWidth + ", swipeActionWidth: " + swipeActionWidth + ", swipeThreshold: " + swipeWidthThreshold);
    }


    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }
}