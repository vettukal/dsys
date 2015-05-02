package com.iiitd.hostel.Database;

/**
 * Created by ruchika on 4/17/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruchika on 4/13/15.
 */
public class ListDBHelper extends SQLiteOpenHelper {

    public static final String LIST = "List";
    public static final String ID = "id";
    public static final String Item_ID = "item_id";
    public static final String Item_Name = "name";
    public static final String Item_Quant = "quantity";
    public static final String TimeStamp = "timestamp";
    private static final String DATABASE_NAME = "List.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "create table " + LIST
            + "(" + ID + " integer primary key autoincrement, "
            + Item_ID + " integer, "
            + Item_Name + " text not null, "
            + TimeStamp + " long, "
            + Item_Quant + " int);";

    public ListDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + LIST);
        onCreate(db);
    }

}