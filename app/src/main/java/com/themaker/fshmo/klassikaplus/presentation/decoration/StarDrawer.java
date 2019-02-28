package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;

import javax.inject.Inject;

public class StarDrawer implements SideDrawer {

    @Inject
    Resources resources;

    private Drawable filledStar;
    private Drawable outlinedStar;

    private final int swipeWidth;
    private final int swipeWidthThreshold;

    public StarDrawer() {
        App.getInstance().getComponent().inject(this);
        filledStar = resources.getDrawable(R.drawable.baseline_star_24px);
        outlinedStar = resources.getDrawable(R.drawable.baseline_star_border_24px);
        swipeWidth = (int) resources.getDimension(R.dimen.recycler_swipe_button_width);
        swipeWidthThreshold = (int) resources.getDimension(R.dimen.recycler_swipe_threshold);
    }

    @Override
    public RectF drawButton(@NonNull Canvas canvas, @NonNull View view, boolean filled) {
        if (filled) {
            filledStar.setBounds(view.getLeft(), view.getTop(), view.getLeft() + swipeWidth + swipeWidthThreshold, view.getBottom());
            filledStar.draw(canvas);
        } else {
            outlinedStar.setBounds(view.getLeft(), view.getTop(), view.getLeft() + swipeWidth + swipeWidthThreshold, view.getBottom());
            outlinedStar.draw(canvas);
        }

        return null;
    }

    protected void drawText(@NonNull String text,
                            @NonNull Canvas canvas,
                            @NonNull RectF button) {
        float textSize = 60;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(text);
        canvas.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), paint);
    }
}
