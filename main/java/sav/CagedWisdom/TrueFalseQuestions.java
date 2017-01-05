package sav.CagedWisdom;

import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import sav.honouringcage.R;

public class TrueFalseQuestions extends Activity {
    List<Question> mQuestionList;
    private int mPlayerAScore=0;
    private int mPlayerBScore=0;
    private boolean mCheated = false;
    private int mQuestionID;
    private int mProgress = 0;
    private Question mCurrentQuestion;
    private TextView mTextQuestion, mPlayerNameTextView;
    private final QuestionDatabase DB=new QuestionDatabase(this);
    private String mGameMode, mPlayerAName, mPlayerBName;
    private TextView mPlayerAScoreView, mPlayerBScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setGameMode(extras.getString("gameMode"));
        setPlayerAName(extras.getString("playerAName"));
        setPlayerBName(extras.getString("playerBName"));

        setContentView(R.layout.true_false_layout);

        mTextQuestion = (TextView) findViewById(R.id.question_text_view);
        mPlayerNameTextView = (TextView) findViewById(R.id.playerName_textview);

        final Button mTrueButton;
        final Button mFalseButton;
        final Button mSkipButton = (Button) findViewById(R.id.skip_button);
        final Button mCheatButton = (Button) findViewById(R.id.cheat_button);
        final Button mMainButton = (Button) findViewById(R.id.main_menu_button);
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);

        mPlayerAScoreView = (TextView) findViewById(R.id.playerA_score);
        mPlayerBScoreView = (TextView) findViewById(R.id.playerB_score);

        changePlayer();

        mQuestionList = DB.getQuestions();

        //  Randomises first question
        mQuestionID = ((int)(Math.random() * 10));
        nextQuestion();

        final int MODEPROGRESSMAX;
        int mGameProgressMax;

        if(getGameMode().equalsIgnoreCase("mp")) {
            MODEPROGRESSMAX = 10;
            mGameProgressMax = 20;
        }else {
            MODEPROGRESSMAX = 5;
            mGameProgressMax = 10;
        }

        progressBar.setMax(mGameProgressMax);
        progressBar.setProgress(mProgress);


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton.setBackgroundResource(android.R.drawable.btn_default);
        mFalseButton.setBackgroundResource(android.R.drawable.btn_default);
        mCheatButton.setBackgroundResource(android.R.drawable.btn_default);
        mSkipButton.setBackgroundResource(android.R.drawable.btn_default);
        mMainButton.setBackgroundResource(android.R.drawable.btn_default);

        setScoreText();

        //  Precondition: True button clicked...
        //  If user selects true & answertrue (1) then makes a toast for "Correct" & increases score
        //      If answer is not true (0) then makes a toast for "Incorrect" no increase to score
        //      If user mCheated then doesn't increase score.
            mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isCheated()) {
                        if (mCurrentQuestion.getAnswerTrue() == 1) {
                            Toast.makeText(TrueFalseQuestions.this, R.string.correctToast, Toast.LENGTH_SHORT)
                                    .show();
                            addScore();
                        } else {
                            Toast.makeText(TrueFalseQuestions.this, R.string.incorrectToast, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    setCheated(false);
                    mProgress++;
                    mTrueButton.setBackgroundResource(android.R.drawable.btn_default);
                    mFalseButton.setBackgroundResource(android.R.drawable.btn_default);
                    setScoreText();
                    if (mProgress == MODEPROGRESSMAX){
                        nextMode();
                    }else {
                        progressBar.setProgress(mProgress);
                        setIsAsked();
                        nextQuestion();
                    }
                }
            });

        //  Precondition: False button clicked...
        //  If user selects true & answerfalse (0) then makes a toast for "Correct" & increases score
        //      If answer is not true (0) then makes a toast for "Incorrect" no increase to score
        //      If user mCheated then doesn't increase score.
            mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCheated()) {
                    if (mCurrentQuestion.getAnswerTrue() == 1) {
                        Toast.makeText(TrueFalseQuestions.this, R.string.incorrectToast, Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(TrueFalseQuestions.this, R.string.correctToast, Toast.LENGTH_SHORT)
                                .show();
                        addScore();
                    }
                }
                setCheated(false);
                mProgress++;
                setScoreText();
                mTrueButton.setBackgroundResource(android.R.drawable.btn_default);
                mFalseButton.setBackgroundResource(android.R.drawable.btn_default);
                if (mProgress == MODEPROGRESSMAX)
                    nextMode();
                else {
                    progressBar.setProgress(mProgress);
                    setIsAsked();
                    nextQuestion();
                }
            }
        });

            mSkipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TrueFalseQuestions.this, R.string.skippedToast, Toast.LENGTH_SHORT)
                            .show();
                    mProgress++;
                    setScoreText();
                    mTrueButton.setBackgroundResource(android.R.drawable.btn_default);
                    mFalseButton.setBackgroundResource(android.R.drawable.btn_default);
                    if (mProgress == MODEPROGRESSMAX) {
                        nextMode();
                    }else {
                        progressBar.setProgress(mProgress);
                        setIsAsked();
                        nextQuestion();
                    }

                }
            });

            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCheated(true);
                    if (mCurrentQuestion.getAnswerTrue() == 1)
                        mTrueButton.setBackgroundColor(Color.RED);
                    else
                        mFalseButton.setBackgroundColor(Color.RED);
                }
            });


        mMainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TrueFalseQuestions.this, QuizOpening.class));
                }
            });


    }

    //  Precondition: Question was asked (and then skipped or answered)
    //  Updates the relevant SQL row by setting isAsked to 1 (true)
    private void setIsAsked(){
        mQuestionList.get(mQuestionID).setIsAsked(1);
        DB.updateQuestionAsked(mQuestionList.get(mQuestionID));
    }

    //  Precondition: The user answered the question correctly.
    //  If the game mode is single player adds to Player A's score.
    //      If multiplayer determines who to add to by taking the remainder of the current mProgress
    //      compared with 2. As Player A always starts, the remainder with 2 will always be 0.
    //      (Add 2 for the sake of mProgress 0 and 1)
    private void addScore(){
        if(getGameMode().equalsIgnoreCase("mp")) {
            if ((mProgress + 2) % 2 == 0)
                mPlayerAScore++;
            else
                mPlayerBScore++;
        }else{
            mPlayerAScore++;
        }
    }

    //  Precondition: The user names are accessible. In multiplayer mode. Called from nextQuestion()
    //  Sets the name of the current user(s) term by taking the remainder of the current mProgress
    //      compared with 2. As Player A always starts, the remainder with 2 will always be 0.
    //      (Add 2 for the sake of mProgress 0 and 1)
    private void changePlayer(){
        if((mProgress + 2) % 2 == 0)
            mPlayerNameTextView.setText(getPlayerAName());
        else
            mPlayerNameTextView.setText(getPlayerBName());
    }

    //  Precondition: SQLColumn to determine if question has been asked before. 0 - false, 1 - true.
    //  Progresses to next question. Randomises between all multiple choice questions (1 - 10)
    //    Selects if it hasn't already been asked.
    private void nextQuestion(){
        while(mQuestionList.get(mQuestionID).getIsAsked() == 1){
            mQuestionID = ((int)(Math.random() * 10));
        }
        mCurrentQuestion=mQuestionList.get(mQuestionID);
        mTextQuestion.setText(mCurrentQuestion.getQuestion());
        setCheated(false);
        if(getGameMode().equalsIgnoreCase("mp"))
            changePlayer();
    }

    //  Precondition: The game mode, user score(s) and user name(s) are accessible and known
    //  Selects the next mode (question type). Creates an intent for a new activity and adds
    //      Player A's name and score and if applicable Player B's name and score, to the intent.
    private void nextMode(){
        Intent i = new Intent(TrueFalseQuestions.this, MultipleChoice.class);
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

    //  Precondition: Scores for  player(s) are name
    //  Puts the scores for player(s) in top-right of screen
    private void setScoreText(){
        mPlayerAScoreView.setText(getPlayerAName() + " Score: " + mPlayerAScore);
        if(getGameMode().equalsIgnoreCase("mp"))
            mPlayerBScoreView.setText(getPlayerBName() + " Score: " + mPlayerBScore);
        else
            mPlayerBScoreView.setText("");
    }

    //  Accessor and mutator methods for encapsulation

    public boolean isCheated() {
        return mCheated;
    }

    public void setCheated(boolean mCheated) {
        this.mCheated = mCheated;
    }

    public String getGameMode() {
        return mGameMode;
    }

    public void setGameMode(String gameMode) {
        mGameMode = gameMode;
    }

    public String getPlayerAName() {
        return mPlayerAName;
    }

    public void setPlayerAName(String playerAName) {
        mPlayerAName = playerAName;
    }

    public String getPlayerBName() {
        return mPlayerBName;
    }

    public void setPlayerBName(String playerBName) {
        mPlayerBName = playerBName;
    }


}
