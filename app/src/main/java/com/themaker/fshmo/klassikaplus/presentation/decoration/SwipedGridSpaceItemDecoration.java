package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltySwipeController;

public class SwipedGridSpaceItemDecoration extends GridSpaceItemDecoration {

    private final NoveltySwipeController swipeController;

    public SwipedGridSpaceItemDecoration(int verticalSpaceHeight, int horizontalSpaceWidth, NoveltySwipeController swipeController) {
        this.horizontalSpaceWidth = horizontalSpaceWidth;
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.swipeController = swipeController;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        swipeController.onDraw(c);
        super.onDraw(c, parent, state);
    }
}