package com.customviews.fancyimageviews.transforms;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

/**
 * @author Manish Kumar
 * @since 11/7/17
 */


public class CircularTransformation extends ViewTransformation {

    float centerX;
    float centerY;
    float radius;

    public float getCenterX () {
        return centerX;
    }

    public void setCenterX (float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY () {
        return centerY;
    }

    public void setCenterY (float centerY) {
        this.centerY = centerY;
    }

    public float getRadius () {
        return radius;
    }

    public void setRadius (float radius) {
        this.radius = radius;
    }

    @Override
    public boolean transform (Canvas canvas, View view) {

        path.reset();
        path.addCircle(centerX, centerY, radius,
                Path.Direction.CW);
        canvas.clipPath(path, op);
        return false;
    }
}
