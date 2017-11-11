package com.example.pranjalkaler.paleen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.view.View.GONE;

public class PalindromeGame extends AppCompatActivity {

    private LinearLayout horizontalLayout;
    private Button start;
    private String generatedString;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palindrome_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        start = findViewById(R.id.startGame);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
                start.setEnabled(false);
                start.setVisibility(GONE);
            }
        });


        findViewById(R.id.nextround).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                Intent intent = new Intent(PalindromeGame.this, NQueen.class);
                startActivity(intent);
                finish();
            }
        });

        message = findViewById(R.id.message);

        horizontalLayout = findViewById(R.id.vertical_layout);
        horizontalLayout.setOnDragListener(new DragListener());

        findViewById(R.id.parentLayout).setOnDragListener(new DragListener());

    }

    private void startGame() {
        horizontalLayout.setVisibility(View.VISIBLE);
        Random r = new Random();
        int check = r.nextInt(7) + 3;
        char c;
        String ch;
        generatedString ="";
        int i;
        for(i = 0; i<check; i++) {
            c = (char) (r.nextInt(26)+65);
            generatedString = generatedString + c;
            LetterTile tile = new LetterTile(PalindromeGame.this, c);
            horizontalLayout.addView(tile);
        }
        c = generatedString.charAt(r.nextInt(generatedString.length()));
        generatedString = generatedString + c;
        LetterTile tile = new LetterTile(PalindromeGame.this, c);
        horizontalLayout.addView(tile);
        //Toast.makeText(this, "Parent String: " + generatedString, Toast.LENGTH_SHORT).show();
    }

    private boolean checkIfUserWon(String str) {
        if(checkIfPalin(str)) {
            Toast.makeText(this, "You have cleared this round!!", Toast.LENGTH_SHORT).show();

            findViewById(R.id.nextround).setVisibility(View.VISIBLE);
            findViewById(R.id.message).setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    private void playComputer() {
        String ch = getNextChar(generatedString);

        message.setText("Computer Removed " + ch);

        removeCharFromUI(ch);
        generatedString = generatedString.replaceFirst(ch,"");

        if(checkIfPalin(generatedString)) {
            Toast.makeText(this, "Computer Wins!", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(PalindromeGame.this, LosingScreen.class);
            startActivity(intent);
            finish();
        }
    }

    private void removeCharFromUI(String ch) {

        for(int i = 0; i< generatedString.length(); i++) {
            LetterTile temp = (LetterTile) horizontalLayout.getChildAt(i);
            if(temp.getLetter().toString().equals(ch)) {
                temp.setVisibility(GONE);
                break;
            }
        }
    }

    private boolean checkIfPalin(String str) {
        int frequency[] = new int[26];
        int no_of_odds = 0;
        int i;
        for(i = 0; i < 26; i++)
            frequency[i] = 0;
        for(i = 0; i < str.length(); i++)
            ++frequency[((int) (str.charAt(i))) - 65];
        for(i = 0; i < 26; i++) {
            if(frequency[i] % 2 != 0){
                ++no_of_odds;
            }
        }
        if(no_of_odds <= 1)
            return true;
        return false;
    }

    private String getNextChar(String str) {
        int i;
        String temp;
        char[] chars;
        chars = str.toCharArray();
        for(i = 0; i < str.length() ; i++) {
            temp = str.replaceFirst(String.valueOf(chars[i]), "");
            if(checkIfPalin(temp))
                return String.valueOf(chars[i]);
        }

        int frequency[] = new int[26];
        for(i = 0; i < 26; i++)
            frequency[i] = 0;
        for(i = 0; i < str.length(); i++) {
            ++frequency[((int) (str.charAt(i))) - 65];
        }
        for(i = 0; i < 26; i++) {
            if(frequency[i]%2!=0){
                return String.valueOf((char)(i + 65));
            }
        }
        return str.substring(0, 1);
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    ((LetterTile) event.getLocalState()).setVisibility(GONE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    v.invalidate();
                    if(v == findViewById(R.id.parentLayout)) {
                        LetterTile temp = (LetterTile) event.getLocalState();
                        generatedString = generatedString.replace(temp.getLetter().toString(), "");
                        temp.setVisibility(View.GONE);
                        if(!checkIfUserWon(generatedString))
                            playComputer();
                    }
                    else {
                        ((LetterTile) event.getLocalState()).setVisibility(View.VISIBLE);
                    }
                    return true;
            }
            return false;
        }
    }

}
