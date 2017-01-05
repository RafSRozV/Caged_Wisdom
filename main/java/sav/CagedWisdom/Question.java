package sav.CagedWisdom;

public class Question {
    private int mAnswerTrue;
    private int mTextResId;
    private String mAnswer;
    private String mQuestion;
    private String mRadioA;
    private String mRadioB;
    private String mRadioC;
    private String mRadioD;
    private int mIsAsked;


    //  Basic Constructor

    public Question(){
        mTextResId = 0;
        mAnswer = "";
        mQuestion = "";
        mRadioA = "";
        mRadioB = "";
        mRadioC = "";
        mRadioD = "";
        mIsAsked = 0;
        mAnswerTrue = -1;

    }

    //  Precondition: 7 inputs: ID, question, which answer is true, radio texts
    //  Constructor used for multiple choice questions. Sets mAnswerTrue as -1
    //      for all non-true/false qs. Initialises mIsAsked to 0


    public Question(int textResId, String questionText, String answer, String radioA, String radioB,
                    String radioC, String radioD){
        mTextResId = textResId;
        mAnswer = answer;
        mAnswerTrue = -1;
        mQuestion = questionText;
        mRadioA = radioA;
        mRadioB = radioB;
        mRadioC = radioC;
        mRadioD = radioD;
        mIsAsked = 0;
    }


    //  Precondition: 3 inputs: ID, question, answer. (1 true, 0 false) For true/false questions.
    //  Sets values and initialises mIsAsked to 0
    public Question(int textResId, String questionText, int answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mQuestion = questionText;
        mIsAsked = 0;
    }

    //  Precondition: assumes 3 inputs: ID, question, answer
    //  Constructor used for type-answer questions

    public Question(int textResId, String questionText, String answer){
        mTextResId = textResId;
        mAnswer = answer;
        mQuestion = questionText;
        mIsAsked = 0;
        mAnswerTrue = -1;
    }


    //  Accessor and mutator methods for encapsulation

    public int getIsAsked() {
        return mIsAsked;
    }

    public void setIsAsked(int isAsked) {
        mIsAsked = isAsked;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getRadioA() {
        return mRadioA;
    }

    public void setRadioA(String radioA) {
        mRadioA = radioA;
    }

    public String getRadioB() {
        return mRadioB;
    }

    public void setRadioB(String radioB) {
        mRadioB = radioB;
    }

    public String getRadioC() {
        return mRadioC;
    }

    public void setRadioC(String radioC) {
        mRadioC = radioC;
    }

    public String getRadioD() {
        return mRadioD;
    }

    public void setRadioD(String radioD) {
        mRadioD = radioD;
    }

    public int getAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(int answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
