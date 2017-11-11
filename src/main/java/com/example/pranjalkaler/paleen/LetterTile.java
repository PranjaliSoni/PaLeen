package com.example.pranjalkaler.paleen;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class LetterTile extends android.support.v7.widget.AppCompatTextView {

    public static final int TILE_SIZE = 120;
    private Character letter;
    public LetterTile(Context context, Character letter) {
        super(context);
        this.letter = letter;
        setText(this.letter.toString());
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setHeight(TILE_SIZE);
        setWidth(TILE_SIZE);
        setTextSize(25);
        setBackgroundColor(Color.parseColor("#90CAF9"));
    }

    public Character getLetter() {
        return this.letter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            startDrag(ClipData.newPlainText("", ""), new View.DragShadowBuilder(this), this, 0);
        }

        return super.onTouchEvent(motionEvent);
    }
}