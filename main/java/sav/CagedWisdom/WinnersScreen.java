package sav.CagedWisdom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sav.honouringcage.R;

public class WinnersScreen extends AppCompatActivity{

    private TextView mWinnerTextView, mPlayerANameView, mPlayerBNameView,
            mPlayerAScoreView, mPlayerBScoreView;
    private int mPlayerAScore, mPlayerBScore;
    private String mPlayerAName, mPlayerBName, mGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winning_screen_layout);


        Bundle extras = getIntent().getExtras();
        setGameMode(extras.getString("gameMode"));
        setPlayerAName(extras.getString("playerAName"));
        setPlayerBName(extras.getString("playerBName"));
        mPlayerAScore = extras.getInt("playerAScore");
        mPlayerBScore = extras.getInt("playerBScore");


        mWinnerTextView = (TextView) findViewById(R.id.winner_text_view);
        mPlayerANameView = (TextView) findViewById(R.id.player_A_name);
        mPlayerBNameView = (TextView) findViewById(R.id.player_B_name);
        mPlayerAScoreView = (TextView) findViewById(R.id.player_A_score);
        mPlayerBScoreView = (TextView) findViewById(R.id.player_B_score);

        String mWinner;

        //  Preconditions: Player A and B score are known and int
        //  If A has higher score then sets String A wins, otherwise if B higher B wins, otherwise draw
        if(mPlayerAScore > mPlayerBScore)
            mWinner = getPlayerAName();
        else if(mPlayerBScore > mPlayerAScore)
            mWinner = getPlayerBName();
        else
            mWinner = "draw";


        //  Preconditions: mWinner has been set for multiplayer mode to output who won
        //  If single player then just outputs final score, otherwise displays who won and scores.
        if(getGameMode().equalsIgnoreCase("sp")){
            mPlayerANameView.setText(getPlayerAName());
            mPlayerAScoreView.setText("Score: " + mPlayerAScore);
        }else{
            if(mWinner == "draw")
                mWinnerTextView.setText("It's a draw!");
            else
                mWinnerTextView.setText(mWinner + " WINS!");
            mPlayerANameView.setText(getPlayerAName());
            mPlayerAScoreView.setText("Score: " + mPlayerAScore);
            mPlayerBNameView.setText(getPlayerBName());
            mPlayerBScoreView.setText("Score: " + mPlayerBScore);
        }


        Button leaderBoard=(Button)findViewById(R.id.leaderboard_button);
        leaderBoard.setBackgroundResource(android.R.drawable.btn_default);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WinnersScreen.this, LeaderboardScreen.class);
                startActivity(i);
            }
        });

        Button mainMenu = (Button) findViewById(R.id.main_menu_button);
        mainMenu.setBackgroundResource(android.R.drawable.btn_default);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinnersScreen.this, QuizOpening.class));
            }
        });

    }


    //  Accessor and mutator methods for encapsulation


    public String getPlayerBName() {
        return mPlayerBName;
    }

    public void setPlayerBName(String playerBName) {
        mPlayerBName = playerBName;
    }

    public String getPlayerAName() {
        return mPlayerAName;
    }

    public void setPlayerAName(String playerAName) {
        mPlayerAName = playerAName;
    }

    public String getGameMode() {
        return mGameMode;
    }

    public void setGameMode(String gameMode) {
        mGameMode = gameMode;
    }

}
