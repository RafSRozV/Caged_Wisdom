package sav.CagedWisdom;


public class Leaderboard {
    private String mUsername;
    private int mScore;


    //  Precondition: None
    public Leaderboard(){
        mUsername = "NIC";
        mScore = 0;
    }

    //  Precondition: Two inputs, name and score, no ID.
    //  Constructor used for all inputs

    public Leaderboard(String userName, int score){
        mUsername = userName;
        mScore = score;
    }

    //  Gets the username passed into the constructor
    public String getUsername() {
        return mUsername;
    }


    //  Accessors and mutators used throughout the program


    public void setUsername(String username) {
        mUsername = username;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
