package sav.CagedWisdom;

import java.lang.reflect.Type;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import sav.honouringcage.R;

public class TypeAnswerQuestions extends Activity {
    private List<Question> quesList;
    private boolean cheated = false;
    private int qid=20;
    private int progress;

    private final QuestionDatabase DB=new QuestionDatabase(this);
    private String mGameMode, mPlayerAName, mPlayerBName;
    private TextView mPlayerAScoreView, mPlayerBScoreView;
    private Question currentQ;
    private TextView txtQuestion, mPlayerNameTextView;
    private int mPlayerAScore, mPlayerBScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setGameMode(extras.getString("gameMode"));
        setPlayerAName(extras.getString("playerAName"));
        setPlayerBName(extras.getString("playerBName"));
        mPlayerAScore = extras.getInt("playerAScore");
        mPlayerBScore = extras.getInt("playerBScore");


        final LeaderboardDatabase LEADERBOARDDB =new LeaderboardDatabase(this);

        setContentView(R.layout.type_question_layout);

        txtQuestion = (TextView) findViewById(R.id.question_text_view);

        quesList = DB.getQuestions();



        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);

        mPlayerAScoreView = (TextView) findViewById(R.id.playerA_score);
        mPlayerBScoreView = (TextView) findViewById(R.id.playerB_score);
        mPlayerNameTextView = (TextView) findViewById(R.id.playerName_textview);

        //  Randomises first question
        qid = ((int)(Math.random() * 10) + 20);
        nextQuestion();

        final EditText mEditButton = (EditText) findViewById(R.id.typed_answer);

        //  Makes sure the user can't type in an answer longer than the actual answer.
        //  Data validation method.
        mEditButton.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(currentQ.getAnswer().length())});


        final Button CHEATBUTTON = (Button) findViewById(R.id.cheat_button);
        CHEATBUTTON.setBackgroundResource(android.R.drawable.btn_default);

        final int GAMEPROGRESSMAX;

        if(getGameMode().equalsIgnoreCase("mp")) {
            progress = 16;
            GAMEPROGRESSMAX = 20;
        }else {
            progress = 8;
            GAMEPROGRESSMAX = 10;
        }

        progressBar.setMax(GAMEPROGRESSMAX);
        progressBar.setProgress(progress);


        setScoreText();

        //  Precondition: Submit button selected...
        //  Compares user inputted answer to actual answer (case insensitive) and outputs
        //  correct or incorrect if they are right or wrong...
        //  Adds score if correct, nothing if incorrect or cheated

            Button mSubmitButton;


            mSubmitButton = (Button) findViewById(R.id.submit_button);
            mSubmitButton.setBackgroundResource(android.R.drawable.btn_default);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isCheated()) {
                        if (currentQ.getAnswer().equalsIgnoreCase(
                                mEditButton.getText().toString())) {
                            Toast.makeText(TypeAnswerQuestions.this, R.string.correctToast, Toast.LENGTH_SHORT)
                                    .show();
                            addScore();
                        } else {
                            Toast.makeText(TypeAnswerQuestions.this, R.string.incorrectToast,Toast.LENGTH_SHORT)
                                    .show();
                        }
                        setScoreText();
                    }
                    setCheated(false);
                    progress++;
                    if (progress == GAMEPROGRESSMAX) {
                        Leaderboard mNewLeaderA =new Leaderboard(getPlayerAName(), mPlayerAScore);
                        Leaderboard mNewLeaderB =new Leaderboard(getPlayerBName(), mPlayerBScore);
                        LEADERBOARDDB.getReadableDatabase();
                        LEADERBOARDDB.addLeader(mNewLeaderA, true);
                        LEADERBOARDDB.addLeader(mNewLeaderB, true);
                        nextMode();
                    }else {
                        mEditButton.setText("");
                        progressBar.setProgress(progress);
                        setIsAsked();
                        nextQuestion();
                        mEditButton.setFilters(new InputFilter[]{
                                new InputFilter.LengthFilter(currentQ.getAnswer().length())});
                    }
                }
            });

            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setBackgroundResource(android.R.drawable.btn_default);

            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(TypeAnswerQuestions.this, R.string.skippedToast, Toast.LENGTH_SHORT)
                            .show();
                    progress++;
                    if (progress == GAMEPROGRESSMAX) {
                        Leaderboard mNewLeaderA =new Leaderboard(getPlayerAName(), mPlayerAScore);
                        Leaderboard mNewLeaderB =new Leaderboard(getPlayerBName(), mPlayerBScore);
                        LEADERBOARDDB.getReadableDatabase();
                        LEADERBOARDDB.addLeader(mNewLeaderA, true);
                        LEADERBOARDDB.addLeader(mNewLeaderB, true);
                        nextMode();
                    }else {
                        progressBar.setProgress(progress);
                        mEditButton.setText("");
                        setIsAsked();
                        nextQuestion();
                        mEditButton.setFilters(new InputFilter[]{
                                new InputFilter.LengthFilter(currentQ.getAnswer().length())});
                    }
                }
            });

            Button mainMenu = (Button) findViewById(R.id.main_menu_button);
            mainMenu.setBackgroundResource(android.R.drawable.btn_default);
            mainMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TypeAnswerQuestions.this, QuizOpening.class));
                }
            });


        CHEATBUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setCheated(true);
                    mEditButton.setText(currentQ.getAnswer());
            }
        });
    }


    //  Precondition: The game mode, user score(s) and user name(s) are accessible and known
    //  Selects the next mode (question type). Creates an intent for a new activity and adds
    //      Player A's name and score and if applicable Player B's name and score, to the intent.
    private void nextMode(){
        Intent i = new Intent(TypeAnswerQuestions.this, WinnersScreen.class);
        i.putExtra("playerAScore", mPlayerAScore);
        i.putExtra("playerAName", getPlayerAName());

        if (getGameMode().equalsIgnoreCase("sp"))
            i.putExtra("gameMode", "sp");
        else if ((getGameMode().equalsIgnoreCase("mp"))) {
            i.putExtra("playerBName", getPlayerBName());
            i.putExtra("playerBScore", mPlayerBScore);
            i.putExtra("gameMode", "mp");
        }
        startActivity(i);
    }

    //  Precondition: Question was asked (and then skipped or answered)
    //  Updates the relevant SQL row by setting isAsked to 1 (true)
    private void setIsAsked(){
        quesList.get(qid).setIsAsked(1);
        DB.updateQuestionAsked(quesList.get(qid));
    }

    //  Precondition: The user answered the question correctly.
    //  If the game mode is single player adds to Player A's score.
    //      If multiplayer determines who to add to by taking the remainder of the current progress
    //      compared with 2. As Player A always starts, the remainder with 2 will always be 0.
    //      (Add 2 for the sake of progress 0 and 1)
    private void addScore(){
        if(getGameMode().equalsIgnoreCase("mp")) {
            if ((progress + 2) % 2 == 0)
                mPlayerAScore++;
            else
                mPlayerBScore++;
        }else{
            mPlayerAScore++;
        }
    }

    //  Precondition: The user names are accessible. In multiplayer mode. Called from nextQuestion()
    //  Sets the name of the current user(s) term by taking the remainder of the current progress
    //      compared with 2. As Player A always starts, the remainder with 2 will always be 0.
    //      (Add 2 for the sake of progress 0 and 1)
    private void changePlayer(){
        if((progress + 2) % 2 == 0)
            mPlayerNameTextView.setText(getPlayerAName());
        else
            mPlayerNameTextView.setText(getPlayerBName());
    }

    //  Precondition: SQLColumn to determine if question has been asked before. 0 - false, 1 - true.
    //  Progresses to next question. Randomises between all multiple choice questions (21 - 30)
    //    Selects if it hasn't already been asked.
    public void nextQuestion() {
        while(quesList.get(qid).getIsAsked() == 1){
            qid = ((int)(Math.random() * 10) + 20);
        }
        currentQ=quesList.get(qid);
        txtQuestion.setText(currentQ.getQuestion());
        if(getGameMode().equalsIgnoreCase("mp"))
            changePlayer();
    }

    //  Precondition: Scores for  player(s) are name
    //  Puts the scores for player(s) in top-right of screen
    public void setScoreText(){
        mPlayerAScoreView.setText(getPlayerAName() + " Score: " + mPlayerAScore);
        if(getGameMode().equalsIgnoreCase("mp"))
            mPlayerBScoreView.setText(getPlayerBName() + " Score: " + mPlayerBScore);
        else
            mPlayerBScoreView.setText("");
    }


    //  Accessor and mutator methods for encapsulation

    public boolean isCheated() {
        return cheated;
    }

    public void setCheated(boolean cheated) {
        this.cheated = cheated;
    }

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
