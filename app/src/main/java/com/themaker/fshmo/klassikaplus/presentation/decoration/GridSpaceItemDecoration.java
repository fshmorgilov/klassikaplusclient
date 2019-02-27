package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltySwipeController;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;
    private final int horizontalSpaceWidth;
    private final NoveltySwipeController swipeController;

    public GridSpaceItemDecoration(int verticalSpaceHeight, int horizontalSpaceWidth, NoveltySwipeController swipeController) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.horizontalSpaceWidth = horizontalSpaceWidth;
        this.swipeController = swipeController;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
        outRect.top = verticalSpaceHeight;
        outRect.left = horizontalSpaceWidth;
        outRect.right = horizontalSpaceWidth;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        swipeController.onDraw(c);
        super.onDraw(c, parent, state);
    }
}