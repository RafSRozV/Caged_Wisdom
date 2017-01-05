package sav.CagedWisdom;


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static sav.CagedWisdom.QuestionDatabase.questTable.NAME;


public class QuestionDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_QUEST = "quest";

    private SQLiteDatabase dbase;

    //  Defines the questions SQLite table: the table name and the column names.
    public static final class questTable{
        public static final String NAME = "questions";

        public static final class Columns{
            public static final String KEY_ID = "id";
            public static final String QUESTION = "question";
            public static final String ANSWER = "answer";
            public static final String RADIOA = "radioA";
            public static final String RADIOB = "radioB";
            public static final String RADIOC = "radioC";
            public static final String RADIOD = "radioD";
            public static final String QUESTIONASKED = "isasked";
            public static final String ANSWERTRUE = "istrue";
        }
    }

    public QuestionDatabase(Context context) {
        super(context, NAME, null, DATABASE_VERSION);
    }

    //  Creates the SQLite leaderboard database and calls addQuestions() to initialise the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        db.execSQL("create table " + questTable.NAME + "(" +
                questTable.Columns.KEY_ID + ", " +
                questTable.Columns.QUESTION + ", " +
                questTable.Columns.ANSWER + ", " +
                questTable.Columns.RADIOA + ", " +
                questTable.Columns.RADIOB + ", " +
                questTable.Columns.RADIOC + ", " +
                questTable.Columns.RADIOD + "," +
                questTable.Columns.QUESTIONASKED + ", " +
                questTable.Columns.ANSWERTRUE +
                ")"
        );
        addQuestions();
    }

    //  Adds questions to initialise the database
    private void addQuestions() {
        Question q1=new Question(1, "Nicolas Cage has previously owned at least one castle", 1);
        this.addQuestion(q1);
        Question q2=new Question(2, "Nicolas Cage once spent over $300,000 for a dinosaur skull", 1);
        this.addQuestion(q2);
        Question q3=new Question(3, "Nicolas Cage chose his last name because of his interest in Marvel's Luke Cage", 1);
        this.addQuestion(q3);
        Question q4=new Question(4, "Nicolas Cage has won the 'Best Global Actor In Motion Pictures' award", 1);
        this.addQuestion(q4);
        Question q5=new Question(5, "When Nicolas Cage dies he will be buried in a nine foot tall pyramid", 1);
        this.addQuestion(q5);
        Question q6=new Question(6, "Nicolas Cage owns a dog called Penny Loafdog", 0);
        this.addQuestion(q6);
        Question q7=new Question(7, "Nicolas Cage's nemesis in Face Off was played by Steve Buscemi", 0);
        this.addQuestion(q7);
        Question q8=new Question(8, "Nicolas Cage starred in Joel Schumacher's Batman & Robin", 0);
        this.addQuestion(q8);
        Question q9=new Question(9, "Nicolas Cage has a tattoo on his back of a shark with a gun", 0);
        this.addQuestion(q9);
        Question q10=new Question(10, "In the film The Wicker Man, Nicolas Cage famously shouts 'Not the trees!'", 0);
        this.addQuestion(q10);
        Question q11=new Question(11, "Which of these animals has Nicolas Cage bought?", "All of the above", "A shark", "An octopus", "A crocodile", "All of the above");
        this.addQuestion(q11);
        Question q12=new Question(12, "In a memorable scene in Vampire's Kiss, what does Nicolas Cage violently recite?", "The alphabet", "The alphabet", "The periodic table", "The recipe for his mother's apple crumble", "The lyrics to Monster Mash");
        this.addQuestion(q12);
        Question q13=new Question(13, "Who is Nicolas Cage related to?", "Both of them", "Jason Schwartzmann", "Francis Ford Coppola", "Both of them", "Neither of them");
        this.addQuestion(q13);
        Question q14=new Question(14, "Nicolas Cage named his son after which superhero?", "Superman", "Superman", "Batman", "Iron Man", "Power Man");
        this.addQuestion(q14);
        Question q15=new Question(15, "Who did Nicolas Cage admit to taking magic mushrooms with?", "His cat", "Steve Buscemi", "Michael Bay", "John Travolta", "His cat");
        this.addQuestion(q15);
        Question q16=new Question(16, "How does Nicolas Cage decide if an animal is worth eating?", "It's sexual habits", "It's dietary habits", "It's sexual habits", "It's excretion habits", "Nothing is worthy of being eaten by him");
        this.addQuestion(q16);
        Question q17=new Question(17, "Which celebrity's autograph did Nicolas Cage obtain to show his love for Patricia Arquette?", "J.D Salinger", "Jack Kerouac", "George Orwell", "J.D Salinger", "Harper Lee");
        this.addQuestion(q17);
        Question q18=new Question(18, "How many films has Nicolas Cage starred in?", "84, but in the time it took you to pick this answer it's probably gone up to 89", "82", "86", "84", "84, but in the time it took you to pick this answer it's probably gone up to 89");
        this.addQuestion(q18);
        Question q19=new Question(19, "What is Nicolas Cage's Bacon number?", "2", "3", "2", "4", "1");
        this.addQuestion(q19);
        Question q20=new Question(20, "Nicolas Cage made his debut in which film?", "Fast Times at Ridgemont High", "The Cotton Club", "Valley Girl", "Zandalee", "Fast Times at Ridgemont High");
        this.addQuestion(q20);
        Question q21=new Question(21, "What is Nicolas Cage's real last name?", "Coppola");
        this.addQuestion(q21);
        Question q22=new Question(22, "What animal did Nicolas Cage actually eat for the film Vampire's Kiss?", "Cockroach");
        this.addQuestion(q22);
        Question q23=new Question(23, "Which film stars Nicolas Cage, Steve Buscemi and John Malkovich?", "Con Air");
        this.addQuestion(q23);
        Question q24=new Question(24, "Which Nicolas Cage film did Charlie Kaufman direct?", "Adaptation");
        this.addQuestion(q24);
        Question q25=new Question(25, "In what year was Nicolas Cage born?", "1964");
        this.addQuestion(q25);
        Question q26=new Question(26, "Who was the female lead in the Nicolas Cage starring film Moonstruck?", "Cher");
        this.addQuestion(q26);
        Question q27=new Question(27, "Fill in the gap: Nicolas Cage's 2010 superhero film was called …-Ass", "Kick");
        this.addQuestion(q27);
        Question q28=new Question(28, "What was the name of the only film directed by Nicolas Cage?", "Sonny");
        this.addQuestion(q28);
        Question q29=new Question(29, "What is Nicolas Cage's middle name?", "Kim");
        this.addQuestion(q29);
        Question q30=new Question(30, "Fill in the gap: Nicolas Cage's 1994 Christmas film was called Trapped in …", "Paradise");
        this.addQuestion(q30);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    //  Precondition: Variables to declare a new question
    //  Inserts a new question into the database
    public void addQuestion(Question aQuestion) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(aQuestion);
        dbase.insert(questTable.NAME, null, values);
    }

    //  Precondition: A new question has been created and instantiated for the Leaderboard
    //  Outputs content values for addQuestion function. Gets variables using accessors.
    private static ContentValues getContentValues(Question aQuestion){
        ContentValues values = new ContentValues();
        values.put(questTable.Columns.KEY_ID, aQuestion.getTextResId());
        values.put(questTable.Columns.QUESTION, aQuestion.getQuestion());
        values.put(questTable.Columns.ANSWER, aQuestion.getAnswer());
        values.put(questTable.Columns.RADIOA, aQuestion.getRadioA());
        values.put(questTable.Columns.RADIOB, aQuestion.getRadioB());
        values.put(questTable.Columns.RADIOC, aQuestion.getRadioC());
        values.put(questTable.Columns.RADIOD, aQuestion.getRadioD());
        values.put(questTable.Columns.QUESTIONASKED, aQuestion.getIsAsked());
        values.put(questTable.Columns.ANSWERTRUE, aQuestion.getAnswerTrue());

        return values;
    }

    //  Precondition: Variables to determine if a WHERE function should be used, null if not
    //  Returns the query for outputting the database
    private Cursor queryQuestions(String whereClause, String[] whereArgs){
        Cursor cursor = dbase.query(
                questTable.NAME,
                null, //Columns
                whereClause,
                whereArgs,
                null, //group
                null, //
                null //orderBy
        );
        return new android.database.CursorWrapper(cursor);
    }

    //  Gets all questions into a list, passes arguments through to queryQuestions()
    public List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        dbase=this.getReadableDatabase();
        Cursor cursor = queryQuestions(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Question quest = new Question();
                quest.setTextResId(cursor.getInt(0));
                quest.setQuestion(cursor.getString(1));
                quest.setAnswer(cursor.getString(2));
                quest.setRadioA(cursor.getString(3));
                quest.setRadioB(cursor.getString(4));
                quest.setRadioC(cursor.getString(5));
                quest.setRadioD(cursor.getString(6));
                quest.setIsAsked(cursor.getInt(7));
                quest.setAnswerTrue(cursor.getInt(8));
                questions.add(quest);
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return questions;
    }

    //  Pre-condition: Method called after submitting (or skipping) a question with arg = 1
    //  Sets question asked (1) to avoid it being asked again
    public void updateQuestionAsked(Question aQuestion){
        int questionID = aQuestion.getTextResId();
        ContentValues values = getContentValues(aQuestion);
        dbase.update(QuestionDatabase.questTable.NAME, values,
                QuestionDatabase.questTable.Columns.QUESTIONASKED + "= ?",
                new String[] {Integer.toString(questionID)});
    }

}
