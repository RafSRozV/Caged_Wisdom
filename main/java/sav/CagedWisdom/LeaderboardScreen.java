package sav.CagedWisdom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sav.honouringcage.R;

public class LeaderboardScreen extends AppCompatActivity{
    private List<Leaderboard> leaderList;
    private Leaderboard currentLeader;
    private final LeaderboardDatabase db=new LeaderboardDatabase(this);

    int lid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String whereClause = null;
        String [] whereArgs = null;

        leaderList = db.getLeaders(whereClause, whereArgs);
        setContentView(R.layout.leaderboard_layout);

        //Preconditions: None
        // Calls the method to set the tables in the leaderboard layout
        setTextview();

        //OnClickListener to return user back to menu
        Button mMenuButton=(Button)findViewById(R.id.main_menu_button);
        mMenuButton.setBackgroundResource(android.R.drawable.btn_default);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaderboardScreen.this, QuizOpening.class);
                startActivity(i);
            }
        });

        //OnClickListener that filters the SQLite table with a WHERE clause
        final Button mFilterButton=(Button)findViewById(R.id.filter_button);
        mFilterButton.setBackgroundResource(android.R.drawable.btn_default);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mUsernameInput = (EditText) findViewById(R.id.filter_name_area);
                String mUsername = mUsernameInput.getText().toString();
                if(mUsername.equalsIgnoreCase("")) {
                    String whereClause = null;
                    String[] whereArgs = null;
                    leaderList = db.getLeaders(whereClause, whereArgs);
                    setTextview();
                    mFilterButton.setText(R.string.Filter);
                    mUsernameInput.setEnabled(true);
                }else {
                    String whereClause = "username =?";
                    String[] whereArgs = {mUsername};
                    leaderList = db.getLeaders(whereClause, whereArgs);
                    setTextview();
                    mFilterButton.setText(R.string.Unfilter);
                    mUsernameInput.setText("");
                    mUsernameInput.setEnabled(false);
                }
            }
        });
    }

    //  Preconditions: The correct layout (leaderboard_layout) has been called
    //  Declares and initialises each TextView in the table
    private void setTextview(){
        TextView mP1Name = (TextView) findViewById(R.id.player1_name);
        TextView mP2Name = (TextView) findViewById(R.id.player2_name);
        TextView mP3Name = (TextView) findViewById(R.id.player3_name);
        TextView mP4Name = (TextView) findViewById(R.id.player4_name);
        TextView mP5Name = (TextView) findViewById(R.id.player5_name);
        TextView mP6Name = (TextView) findViewById(R.id.player6_name);
        TextView mP7Name = (TextView) findViewById(R.id.player7_name);
        TextView mP8Name = (TextView) findViewById(R.id.player8_name);
        TextView mP9Name = (TextView) findViewById(R.id.player9_name);
        TextView mP10Name = (TextView) findViewById(R.id.player10_name);

        TextView mP1Score = (TextView) findViewById(R.id.player1_score);
        TextView mP2Score = (TextView) findViewById(R.id.player2_score);
        TextView mP3Score = (TextView) findViewById(R.id.player3_score);
        TextView mP4Score = (TextView) findViewById(R.id.player4_score);
        TextView mP5Score = (TextView) findViewById(R.id.player5_score);
        TextView mP6Score = (TextView) findViewById(R.id.player6_score);
        TextView mP7Score = (TextView) findViewById(R.id.player7_score);
        TextView mP8Score = (TextView) findViewById(R.id.player8_score);
        TextView mP9Score = (TextView) findViewById(R.id.player9_score);
        TextView mP10Score = (TextView) findViewById(R.id.player10_score);

        //Try-catch statements that catch an out of bounds exception that could occur if no users
        //in table. Possibly as a result of a WHERE clause returning an empty table.
        //If no error, sets text in the leaderboard table in descending score order.
        try {
            lid = 0;
            currentLeader = leaderList.get(lid);
            mP1Name.setText(currentLeader.getUsername());
            mP1Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP1Name.setText(R.string.noUser);
            mP1Score.setText("");
        }

        try {
            lid++;
            currentLeader = leaderList.get(lid);
            mP2Name.setText(currentLeader.getUsername());
            mP2Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP2Name.setText("");
            mP2Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP3Name.setText(currentLeader.getUsername());
            mP3Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP3Name.setText("");
            mP3Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP4Name.setText(currentLeader.getUsername());
            mP4Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP4Name.setText("");
            mP4Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP5Name.setText(currentLeader.getUsername());
            mP5Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP5Name.setText("");
            mP5Score.setText("");
        }

        try {
            lid++;
            currentLeader = leaderList.get(lid);
            mP6Name.setText(currentLeader.getUsername());
            mP6Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP6Name.setText("");
            mP6Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP7Name.setText(currentLeader.getUsername());
            mP7Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP7Name.setText("");
            mP7Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP8Name.setText(currentLeader.getUsername());
            mP8Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP8Name.setText("");
            mP8Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP9Name.setText(currentLeader.getUsername());
            mP9Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP9Name.setText("");
            mP9Score.setText("");
        }

        try{
            lid++;
            currentLeader = leaderList.get(lid);
            mP10Name.setText(currentLeader.getUsername());
            mP10Score.setText(String.valueOf(currentLeader.getScore()));
        }catch(IndexOutOfBoundsException e){
            mP10Name.setText("");
            mP10Score.setText("");
        }
    }
}