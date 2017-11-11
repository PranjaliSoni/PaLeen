package com.example.pranjalkaler.paleen;

import android.content.ClipData;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;


public class Queen extends android.support.v7.widget.AppCompatImageView {
    public Queen(Context context) {
        super(context);
        setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground));
        setMaxWidth(50);
        setMaxHeight(50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            startDrag(ClipData.newPlainText("", ""), new View.DragShadowBuilder(this), this, 0);
        }
        return super.onTouchEvent(motionEvent);
    }
}
