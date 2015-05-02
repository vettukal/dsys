package com.iiitd.hostel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruchika on 4/13/15.
 */
public class ListOperations {

    // Database fields

    private ListDBHelper dbHelper;
    private String[] LIST_TABLE_COLUMNS = {ListDBHelper.ID,ListDBHelper.Item_ID,ListDBHelper.Item_Name,ListDBHelper.TimeStamp,ListDBHelper.Item_Quant};
    private SQLiteDatabase database;
    int item_id_cursor,timestamp_cursor;

    public ListOperations(Context context) {
        dbHelper = new ListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addItem(int item_id, String name,long timestamp,int quantity) {

        ContentValues values = new ContentValues();

        // Checks if the id already exists in the local DB
        Cursor c = database.query(ListDBHelper.LIST,LIST_TABLE_COLUMNS, null, null, null, null, null);
        item_id_cursor = c.getColumnIndex(ListDBHelper.Item_ID);
        timestamp_cursor = c.getColumnIndex(ListDBHelper.TimeStamp);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (item_id == c.getInt(item_id_cursor)) {

                System.out.println("Item_Quant" + c.getInt(c.getColumnIndex(ListDBHelper.Item_Quant)));
                System.out.println(quantity);
                if(timestamp>c.getLong(timestamp_cursor)){

                    values.put(ListDBHelper.TimeStamp,timestamp);
                    values.put(ListDBHelper.Item_Quant,quantity);
                    database.update(ListDBHelper.LIST, values, ListDBHelper.Item_ID + "=" + c.getInt(item_id_cursor), null );
                    return;
                }
                if(timestamp<c.getLong(timestamp_cursor)){
                    return;
                }

            }
        }
        /*Cursor cursor = database.query(ListDBHelper.LIST,
                LIST_TABLE_COLUMNS, ListDBHelper.Item_ID + " = "
                        + item_id, null, null, null,null,null);

        quantity = cursor.getInt(3)+quantity;
        cursor.moveToFirst();*/
        String query = "Select * FROM " + ListDBHelper.LIST;


        /*List<ListDetails> list= new ArrayList<ListDetails>();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();*/
        Log.i("Values normally", "inserted ");
        values.put(ListDBHelper.Item_ID,item_id);
        values.put(ListDBHelper.Item_Name,name);
        values.put(ListDBHelper.Item_Quant,quantity);
        values.put(ListDBHelper.TimeStamp,timestamp);

        long Id = database.insert(ListDBHelper.LIST, null, values);
        // database.insert(DiscussionObjectHelper.DISCUSSION, null, values);

        // now that the student is created return it ...
        /*Cursor cursor = database.query(ListDBHelper.LIST,
                LIST_TABLE_COLUMNS, ListDBHelper.ID + " = "
                        + Id, null, null, null,null,null);
        cursor.moveToFirst();

        ListDetails newComment = parseList(cursor);
        cursor.close();*/
        //return newComment;
    }

    public void deleteDiscussion(String itemname) {


        database.delete(ListDBHelper.LIST, ListDBHelper.Item_Name
                + " = " + "'" + itemname + "'", null);
    }

    public List<ListDetails> getAllItems() {
        String query = "Select * FROM " + ListDBHelper.LIST;


        List<ListDetails> list= new ArrayList<ListDetails>();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            ListDetails dbObject = new ListDetails();
            dbObject.setItem_Id(cursor.getInt(1));
            dbObject.setItem_name(cursor.getString(2));
            dbObject.setQuantity(cursor.getInt(4));
            dbObject.setTimestamp(cursor.getLong(3));
            list.add(dbObject);
            Log.i("Db values:", cursor.getInt(1) + cursor.getString(2) + cursor.getLong(3) + cursor.getInt(4));
            cursor.moveToNext();
        }

        cursor.close();

        database.close();
        return list;
    }

    public long getNoOfRows() {


        return DatabaseUtils.queryNumEntries(database, ListDBHelper.LIST);
    }

    private ListDetails parseList(Cursor cursor) {
        ListDetails listdetails = new ListDetails();
        listdetails.setItem_Id(cursor.getInt(1));
        listdetails.setItem_name(cursor.getString(2));
        listdetails.setQuantity(cursor.getInt(4));
        listdetails.setTimestamp(cursor.getLong(3));

        return listdetails;
    }
}
