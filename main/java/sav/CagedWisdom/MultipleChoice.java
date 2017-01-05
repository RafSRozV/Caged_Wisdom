package sav.CagedWisdom;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import sav.honouringcage.R;

public class MultipleChoice extends Activity {
    List<Question> mQuestionList;
    private  int mPlayerAScore, mPlayerBScore, mProgress;
    private int mQuestionID=10;
    private Question mCurrentQ;
    private TextView mTextQuestion, mPlayerNameTextView, mPlayerAScoreView, mPlayerBScoreView;
    private RadioButton mRadioA, mRadioB, mRadioC, mRadioD;
    private Button mSubmitButton;
    private String mGameMode, mPlayerAName, mPlayerBName;
    private boolean mCheated = false;

    final QuestionDatabase DB=new QuestionDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setGameMode(extras.getString("gameMode"));
        setPlayerAName(extras.getString("playerAName"));
        setPlayerBName(extras.getString("playerBName"));
        mPlayerAScore = extras.getInt("playerAScore");
        mPlayerBScore = extras.getInt("playerBScore");

        setContentView(R.layout.multiple_choice_layout);
        mQuestionList = DB.getQuestions();


        mRadioA = (RadioButton) findViewById(R.id.a_radio);
        mRadioB = (RadioButton) findViewById(R.id.b_radio);
        mRadioC = (RadioButton) findViewById(R.id.c_radio);
        mRadioD = (RadioButton) findViewById(R.id.d_radio);

        mTextQuestion = (TextView) findViewById(R.id.question_text_view);
        mPlayerNameTextView = (TextView) findViewById(R.id.playerName_textview);
        mPlayerAScoreView = (TextView) findViewById(R.id.playerA_score);
        mPlayerBScoreView = (TextView) findViewById(R.id.playerB_score);

        //  Randomises first question
        mQuestionID = ((int)(Math.random() * 10) + 10);
        nextQuestion();

        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);


        //variable to determine maximum questions asked in this question type
        final int MODEPROGRESSMAX;
        //variable to set maximum questions asked in the whole game
        final int GAMEPROGRESSMAX;
        
        if(getGameMode().equalsIgnoreCase("mp")) {
            mProgress = 10;
            MODEPROGRESSMAX = 16;
            GAMEPROGRESSMAX = 20;
        }else {
            mProgress = 5;
            MODEPROGRESSMAX = 8;
            GAMEPROGRESSMAX = 10;
        }

        progressBar.setMax(GAMEPROGRESSMAX);
        progressBar.setProgress(mProgress);


        setQuestionView();

        setScoreText();


            //OnClickListener to check if user selected correct button. If radio group text
            //equals the correct answer then returns "Correct" toast and advances question,
            // "Incorrect" and advances if not
            mSubmitButton = (Button) findViewById(R.id.submit_button);
            mSubmitButton.setBackgroundResource(android.R.drawable.btn_default);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioGroup grp = (RadioGroup) findViewById(R.id.radio_group);
                        try {
                            if(!isCheated()) {
                                RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
                                if (mCurrentQ.getAnswer().equals(answer.getText())) {
                                    Toast.makeText(MultipleChoice.this, R.string.correctToast, Toast.LENGTH_SHORT)
                                            .show();
                                    addScore();
                                } else {
                                    Toast.makeText(MultipleChoice.this, R.string.incorrectToast, Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                                setCheated(false);
                                setScoreText();
                                mProgress++;

                            //Checks if user has answered required amount of questions to mProgress mode
                            if (mProgress == MODEPROGRESSMAX) {
                                nextMode();
                            }else {
                                progressBar.setProgress(mProgress);
                                setIsAsked();
                                nextQuestion();
                            }

                            //Catches a bug that would occur if the user pressed Submit without
                            //  selecting an answer
                        }catch(NullPointerException e){
                            Toast.makeText(MultipleChoice.this, R.string.selectAnswer, Toast.LENGTH_SHORT)
                                    .show();
                        }finally{
                            grp.clearCheck();
                        }
                    }
            });

            //Makes a toast to display "Skipped" then progresses to next question (or mode)
            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setBackgroundResource(android.R.drawable.btn_default);
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioGroup grp = (RadioGroup) findViewById(R.id.radio_group);
                    Toast.makeText(MultipleChoice.this, R.string.skippedToast, Toast.LENGTH_SHORT)
                            .show();
                    grp.clearCheck();
                    mProgress++;
                    if (mProgress == MODEPROGRESSMAX) {
                        nextMode();
                    }else {
                        progressBar.setProgress(mProgress);
                        setIsAsked();
                        nextQuestion();
                    }
                }
            });

        //OnClickListener to return user to main menu
            Button mMenuButton = (Button) findViewById(R.id.main_menu_button);
            mMenuButton.setBackgroundResource(android.R.drawable.btn_default);
            mMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MultipleChoice.this, QuizOpening.class));
                }
            });

        //OnClickListener to cheat. Selects the correct radio button.
        //Makes the isCheatedBoolean True
            Button mCheatButton = (Button) findViewById(R.id.cheat_button);
            mCheatButton.setBackgroundResource(android.R.drawable.btn_default);
            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mRadioA.getText().equals(mCurrentQ.getAnswer()))
                        mRadioA.setChecked(true);
                    if(mRadioB.getText().equals(mCurrentQ.getAnswer()))
                        mRadioB.setChecked(true);
                    if(mRadioC.getText().equals(mCurrentQ.getAnswer()))
                        mRadioC.setChecked(true);
                    if(mRadioD.getText().equals(mCurrentQ.getAnswer()))
                        mRadioD.setChecked(true);
                    setCheated(true);
                }
            });
    }

    //  Precondition: SQLColumn to determine if question has been asked before. 0 - false, 1 - true.
    //  Progresses to next question. Randomises between all multiple choice questions (11 - 20)
    //    Selects if it hasn't already been asked.
    private void nextQuestion()
    {
        while(mQuestionList.get(mQuestionID).getIsAsked() == 1){
            mQuestionID = ((int)(Math.random() * 10) + 10);
        }
        mCurrentQ=mQuestionList.get(mQuestionID);
        setQuestionView();
        if(getGameMode().equalsIgnoreCase("mp"))
            changePlayer();
    }

    //  Precondition: The game mode, user score(s) and user name(s) are accessible and known
    //  Selects the next mode (question type). Creates an intent for a new activity and adds
    //      Player A's name and score and if applicable Player B's name and score, to the intent.
    private void nextMode(){
        Intent i = new Intent(MultipleChoice.this, TypeAnswerQuestions.class);
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


    //  Precondition: Question was asked (and then skipped or answered)
    //  Updates the relevant SQL row by setting isAsked to 1 (true)
    private void setIsAsked(){
        mQuestionList.get(mQuestionID).setIsAsked(1);
        DB.updateQuestionAsked(mQuestionList.get(mQuestionID));
    }

    //  Precondition: The correct layout has been called and the SQL table is accessible
    //  Sets the text for the radio buttons based on the values in the SQL database
    private void setQuestionView() {
        mTextQuestion.setText(mCurrentQ.getQuestion());
        mRadioA.setText(mCurrentQ.getRadioA());
        mRadioB.setText(mCurrentQ.getRadioB());
        mRadioC.setText(mCurrentQ.getRadioC());
        mRadioD.setText(mCurrentQ.getRadioD());
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

    public boolean isCheated() {
        return mCheated;
    }

    public void setCheated(boolean mCheated) {
        this.mCheated = mCheated;
    }



}
