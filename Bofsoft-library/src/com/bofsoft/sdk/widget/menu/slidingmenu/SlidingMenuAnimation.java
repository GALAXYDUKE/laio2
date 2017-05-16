package com.bofsoft.sdk.widget.menu.slidingmenu;

import android.graphics.Canvas;

public class SlidingMenuAnimation {

    public static SlidingMenu.CanvasTransformer ZOOM = new SlidingMenu.CanvasTransformer() {
        public void transformCanvas(Canvas paramCanvas, float paramFloat) {
            float f = (float) (0.75D + 0.25D * paramFloat);
            paramCanvas.scale(f, f, paramCanvas.getWidth() / 2, paramCanvas.getHeight() / 2);
        }
    };

}
