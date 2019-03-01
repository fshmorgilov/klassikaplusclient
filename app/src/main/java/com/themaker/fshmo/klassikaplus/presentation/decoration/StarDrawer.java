package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;

import javax.inject.Inject;

public class StarDrawer implements SideDrawer {

    private static final String TAG = StarDrawer.class.getName();

    @Inject
    Resources resources;

    private Drawable filledStar;
    private Drawable outlinedStar;

    private final int swipeWidth;
    private final int swipeWidthThreshold;

    public StarDrawer() {
        App.getInstance().getComponent().inject(this);
        swipeWidth = (int) resources.getDimension(R.dimen.recycler_swipe_button_width);
        swipeWidthThreshold = (int) resources.getDimension(R.dimen.recycler_swipe_threshold);
        filledStar = resources.getDrawable(R.drawable.ic_baseline_star);
        outlinedStar = resources.getDrawable(R.drawable.ic_baseline_star_border);
    }

    @Override
    public RectF draw(@NonNull Canvas canvas, @NonNull View view, boolean filled) {
        int top = ((view.getBottom() - view.getTop()) / 2) - (swipeWidth + swipeWidthThreshold) / 2;
        int bottom = ((view.getBottom() - view.getTop()) / 2) + (swipeWidth + swipeWidthThreshold) / 2;
        Log.d(TAG, "StarDrawer: view top: " + view.getTop() + ", view bottom: " + view.getBottom());
        Log.d(TAG, "draw: swipewidth & threshold: " + swipeWidth + swipeWidthThreshold);
        if (filled) {
            filledStar.setBounds(view.getLeft(), top, view.getLeft() + swipeWidth + swipeWidthThreshold, bottom);
            filledStar.draw(canvas);
        } else {
            outlinedStar.setBounds(view.getLeft(), top, view.getLeft() + swipeWidth + swipeWidthThreshold, bottom);
            outlinedStar.draw(canvas);
        }
        return null;
    }

}
