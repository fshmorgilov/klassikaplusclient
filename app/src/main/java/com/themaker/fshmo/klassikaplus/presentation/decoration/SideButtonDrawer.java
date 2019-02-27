package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import androidx.annotation.NonNull;

public class SideButtonDrawer implements ButtonDrawer {
    @Override
    public RectF drawButton(@NonNull Canvas canvas,  @NonNull View view) {
        String favorite = "Избранное";
        int buttonWidthWithoutPadding = 30; // FIXME: 2/27/2019 inject
        int cornerRadius = 4;
        Paint paint = new Paint();
        RectF button = new RectF(view.getLeft(), view.getTop(), view.getRight() + buttonWidthWithoutPadding, view.getBottom());
        paint.setColor(Color.YELLOW);
        canvas.drawRoundRect(button, cornerRadius, cornerRadius, paint);
        drawText(favorite, canvas, button);
        return button;
    }

    private void drawText(@NonNull String text,
                          @NonNull Canvas canvas,
                          @NonNull RectF button) {
        float textSize = 60;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(text);
        canvas.drawText(text, button.centerX() - (textWidth/2), button.centerY() + (textSize/2), paint);
    }
}
