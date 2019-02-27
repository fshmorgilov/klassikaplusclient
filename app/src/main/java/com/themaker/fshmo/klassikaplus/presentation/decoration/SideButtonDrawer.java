package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import androidx.annotation.NonNull;

public class SideButtonDrawer implements ButtonDrawer {
    @Override
    public RectF drawButton(@NonNull Canvas canvas, @NonNull View view, boolean left) {
        String favorite = "Избранное";
        int buttonWidthWithoutPadding = 70; // FIXME: 2/27/2019 inject
        int cornerRadius = 4;
        Paint paint = new Paint();
        RectF button;
        if (left) {
            button = new RectF(view.getLeft(), view.getTop(), view.getLeft() + buttonWidthWithoutPadding, view.getBottom());
            paint.setColor(Color.YELLOW);
        } else {
            button = new RectF(view.getRight() - buttonWidthWithoutPadding, view.getTop(), view.getRight(), view.getBottom());
            paint.setColor(Color.RED);
            drawText(favorite, canvas, button);
        }
        canvas.drawRoundRect(button, cornerRadius, cornerRadius, paint);
        return button;
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
