package com.example.pranjalkaler.paleen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.view.View.GONE;

public class NQueen extends AppCompatActivity {


    int[] ids_of_cells = new int[]{R.id.c0, R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.c5, R.id.c6,
            R.id.c7, R.id.c8, R.id.c9, R.id.c10, R.id.c11, R.id.c12, R.id.c13, R.id.c14, R.id.c15};
    int[] row=new int[16];
    int[] column=new int[16];
    int[][]mat=new int[4][4];
    int []placed=new int[16];
    int x,y,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nqueen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        for(int i = 0; i< 16; i++) {
            findViewById(ids_of_cells[i]).setOnDragListener(new DragListener());

            row[i]=i/4;
            column[i]=i%4;

        }
        count=0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
                mat[i][j]=0;
        }
        for(int i=0;i<16;i++)
            placed[i]=0;
        //findViewById(R.id.gridLayout).setOnDragListener(new DragListener());
        Queen Q1 = new Queen(NQueen.this);
        Queen Q2 = new Queen(NQueen.this);
        Queen Q3 = new Queen(NQueen.this);
        Queen Q4 = new Queen(NQueen.this);
        RelativeLayout hostLayout = findViewById(R.id.hostLayout);
        hostLayout.addView(Q1);
        hostLayout.addView(Q2);
        hostLayout.addView(Q3);
        hostLayout.addView(Q4);
        hostLayout.setOnDragListener(new DragListener());


    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    ((Queen) event.getLocalState()).setVisibility(GONE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(getResources().getDrawable(R.drawable.shape_droptarget));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(getResources().getDrawable(R.drawable.shape2));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(getResources().getDrawable(R.drawable.shape2));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    v.invalidate();
                    View view = (View) event.getLocalState();

                    int ID = generateID(v.getId());
                    //Toast.makeText(NQueen.this, "" + ID + "      " + placed[ID], Toast.LENGTH_SHORT).show();

                    if(placed[ID]==1) {
                        view.setVisibility(View.VISIBLE);
                        return true;
                    }
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    RelativeLayout container = (RelativeLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    validate(v);
                    return true;
            }
            return false;
        }
    }

    private int generateID(int id) {
        for(int i = 0; i< ids_of_cells.length; i++) {
            if (ids_of_cells[i] == id)
                return i;
        }
        return 0;
    }

    private void validate(View view) {
        x=row[generateID(view.getId())];
        y=column[generateID(view.getId())];


        if(mat[x][y]==1)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(NQueen.this, LosingScreen.class);
            startActivity(intent);
            finish();
        }
        else if(count<3) {
            placed[generateID(view.getId())]=1;
            count++;
        }
        else
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(NQueen.this, WinningScreen.class);
            startActivity(intent);
            finish();
        }
        for(int k=0;k<4;k++)
        {
            mat[k][y]=1;
        }

        for(int k=0;k<4;k++)
        {
            mat[x][k]=1;
        }
        for(int k=0;k<4;k++)
        {
            if(x+k<4&&y+k<4)
                mat[x+k][y+k]=1;
        }

        for(int k=0;k<4;k++)
        {
            if(x-k>=0&&y-k>=0)
                mat[x-k][y-k]=1;
        }

        for(int k=0;k<4;k++)
        {
            if(x-k>=0&&y+k<4)
                mat[x-k][y+k]=1;
        }
        for(int k=0;k<4;k++)
        {
            if(x+k<4&&y-k>=0)
                mat[x+k][y-k]=1;
        }
    }
}
