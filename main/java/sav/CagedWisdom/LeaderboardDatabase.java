package sav.CagedWisdom;


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static sav.CagedWisdom.LeaderboardDatabase.leaderTable.NAME;


public class LeaderboardDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_QUEST = "leader";
    private SQLiteDatabase dbase;

    //  Defines the leaderboard SQLite table: the table name and the column names.
    public static final class leaderTable{
        
        public static final String NAME = "leaders";

        public static final class Columns{
            private static final String USERNAME = "username";
            private static final String SCORE = "score";
        }
    }

    
    public LeaderboardDatabase(Context context) {
        super(context, NAME, null, DATABASE_VERSION);
    }
    
    //  Creates the SQLite leaderboard database and calls addLeaders() to initialise the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        db.execSQL("create table " + leaderTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                leaderTable.Columns.USERNAME + ", " +
                leaderTable.Columns.SCORE +
                ")"
        );
        addLeaders();
    }
    
    //  Adds a user to database to prevent database crashing on first use
    private void addLeaders() {
            Leaderboard l1 = new Leaderboard();
            this.addLeader(l1, false);
    }
    
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    //  Precondition: Variables to declare a new leader and to determine if it's based
    //               on the original create function or a new insert (i.e. from end of quiz)
    //  Inserts a new user into the database
    public void addLeader(Leaderboard aLeader, Boolean newLeader) {
        SQLiteDatabase db = dbase;
        if(newLeader) db = this.getWritableDatabase();
        ContentValues values = getContentValues(aLeader);
        db.insert(leaderTable.NAME, null, values);
    }

    //  Precondition: A new leader has been created and instantiated for the Leaderboard
    //  Outputs content values for addLeader function. Gets variables using accessors.
    private static ContentValues getContentValues(Leaderboard aLeader){
        ContentValues values = new ContentValues();
        values.put(leaderTable.Columns.USERNAME, aLeader.getUsername());
        values.put(leaderTable.Columns.SCORE, aLeader.getScore());
        return values;
    }

    //  Precondition: Variables to determine if a WHERE function should be used, null if not
    //  Returns the query for outputting the database
    private Cursor queryLeaders(String whereClause, String[] whereArgs){
        Cursor cursor = dbase.query(
        leaderTable.NAME,
        null,
         whereClause,
         whereArgs,
        null,
        null,
        leaderTable.Columns.SCORE + " DESC"); //orderBy
        return new android.database.CursorWrapper(cursor);
    }

    //  Precondition: Variables to determine if a WHERE function should be used for queryLeader(),
    //              null if not
    //  Gets all users into a list, passes arguments through to queryLeaders()
    public List<Leaderboard> getLeaders(String whereClause, String[] whereArgs){
        List<Leaderboard> leaders = new ArrayList<>();
        dbase=this.getReadableDatabase();
        Cursor cursor = queryLeaders(whereClause, whereArgs);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Leaderboard leader = new Leaderboard();
                leader.setUsername(cursor.getString(1));
                leader.setScore(cursor.getInt(2));
                leaders.add(leader);
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return leaders;
    }

}
