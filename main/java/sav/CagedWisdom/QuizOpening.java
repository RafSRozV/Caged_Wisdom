package sav.CagedWisdom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sav.honouringcage.R;

public class QuizOpening extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.opening_layout);


        //  Sets OnClickListeners for each game mode and either passes "sp" or "mp" to
        //  start screen activity, or calls practice mode.


        Button singlePlayer=(Button)findViewById(R.id.single_player_button);
        singlePlayer.setBackgroundResource(android.R.drawable.btn_default);
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizOpening.this, Start_Screen.class);
                i.putExtra("gameMode", "sp");
                startActivity(i);
            }
        });

        Button multiPlayer=(Button)findViewById(R.id.multiplayer_button);
        multiPlayer.setBackgroundResource(android.R.drawable.btn_default);
        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizOpening.this, Start_Screen.class);
                i.putExtra("gameMode", "mp");
                startActivity(i);
            }
        });

        Button practiceMode=(Button)findViewById(R.id.practice_button);
        practiceMode.setBackgroundResource(android.R.drawable.btn_default);
        practiceMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizOpening.this, QuestionList.class);
                i.putExtra("gameMode", "practiceMode");
                startActivity(i);
            }
        });

        //OnClickListener to call leaderboard

        Button leaderBoard=(Button)findViewById(R.id.leaderboard_button);
        leaderBoard.setBackgroundResource(android.R.drawable.btn_default);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizOpening.this, LeaderboardScreen.class);
                startActivity(i);
            }
        });

    }

}
