package sav.CagedWisdom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sav.honouringcage.R;

public class Start_Screen extends AppCompatActivity {

    private String mGameMode;
    private String playerAName;
    private String playerBName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Preconditions: gameMode has been set as an extra in QuizOpening
        //  Gets gameMode from QuizOpening

        Bundle extras = getIntent().getExtras();
        setGameMode(extras.getString("gameMode"));

        //  Selects layout based on gameMode (sp = single player, mp = multiplayer)
        if (getGameMode().equalsIgnoreCase("sp")) {
            setContentView(R.layout.sp_start_screen);
        } else {
            setContentView(R.layout.mp_start_screen);
        }

        Button mainMenu = (Button) findViewById(R.id.main_menu_button);
        mainMenu.setBackgroundResource(android.R.drawable.btn_default);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start_Screen.this, QuizOpening.class));
            }
        });

        //  Selects the next mode once user registers with the system and presses the start button
        Button startQuiz = (Button) findViewById(R.id.start_button);
        startQuiz.setBackgroundResource(android.R.drawable.btn_default);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMode();
            }
        });

    }

    private void nextMode() {
        Intent i = new Intent(Start_Screen.this, TrueFalseQuestions.class);
        EditText mPlayerAName = (EditText) findViewById(R.id.playerA_name);
        String playerAName = mPlayerAName.getText().toString();

        //  Precondition: Player A has inputted name and is accessible
        //  Checks if length is 3 and doesn't contain any spaces
        if ((playerAName.length() != 3) || (playerAName.contains(" "))) {
            Toast.makeText(Start_Screen.this, R.string.nameValidationPlayerA, Toast.LENGTH_SHORT)
                    .show();
        } else {

            setPlayerAName(playerAName);
            i.putExtra("playerAName", getPlayerAName());

            if (getGameMode().equalsIgnoreCase("sp")) {
                i.putExtra("gameMode", "sp");
                startActivity(i);
            }

            else if ((getGameMode().equalsIgnoreCase("mp"))) {
                EditText mPlayerBName = (EditText) findViewById(R.id.playerB_name);
                String playerBName = mPlayerBName.getText().toString();

                //  Preconditions: Multiplayer mode. Player B has inputted name and is accessible
                //  Checks if length is 3 and doesn't contain any spaces
                if ((playerBName.length() != 3) || (playerBName.contains(" "))) {
                    Toast.makeText(Start_Screen.this, R.string.nameValidationPlayerB, Toast.LENGTH_SHORT)
                            .show();
                }else {
                    setPlayerBName(playerBName);
                    i.putExtra("playerBName", getPlayerBName());
                    i.putExtra("gameMode", "mp");
                    startActivity(i);
                }
            }
        }
    }


    //  Accessor and mutator methods for encapsulation


    public String getPlayerAName() {
        return playerAName;
    }

    public void setPlayerAName(String playerAName) {
        this.playerAName = playerAName;
    }

    public String getPlayerBName() {
        return playerBName;
    }

    public void setPlayerBName(String playerBName) {
        this.playerBName = playerBName;
    }

    public String getGameMode() {
        return mGameMode;
    }

    public void setGameMode(String gameMode) {
        mGameMode = gameMode;
    }
}
