package com.themaker.fshmo.klassikaplus.presentation.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import androidx.annotation.NonNull;

public interface SideDrawer {

    RectF drawButton(@NonNull Canvas canvas,
                    @NonNull View view, boolean filled);

}
