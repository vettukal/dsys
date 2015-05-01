package com.iiitd.hostel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iiitd.hostel.Database.ListOperations;
import com.iiitd.hostel.Database.LocalDatabase;
import com.iiitd.hostel.backend.quoteApi.model.Quote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 4/27/2015.
 */


public class SyncServer extends AsyncTask<Void,Void,String> {
    private Context context;
    int ItemId, quant;


    public SyncServer(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Void... params) {
        // Fectching local database.
        LocalDatabase ldb = new LocalDatabase();
        List<Quote> localDB = ldb.getLocalQuotes(context);
        for(Quote q: localDB){
            Log.d("vince serversync","number from localDB: "+q.getItemId());
        }

        // Fetchng GAE database.
        QuoteConnector qc = new QuoteConnector(0);
        List<Quote> serverDB = null;
        try {
            serverDB = qc.listQuote();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Quote q: serverDB){
            Log.d("vince serversync","number is: "+q.getItemId());
        }

        // Now we have to compare the both the local and server side and then make the stuff.

        addToServer(localDB,serverDB);

        // Now we have to compare both and add it to localDB.

        addToLocalDB(localDB,serverDB);
        return null;
    }

    private void addToLocalDB(List<Quote> localDB, List<Quote> serverDB) {
        List<Quote> addList = new ArrayList<>();
        for(Quote serverQ: serverDB){
            boolean isFound  = false;
            for(Quote localQ: localDB){
                if(localQ.getItemId()==serverQ.getItemId()){
                    isFound = true;
                }
            }
            addList.add(serverQ);
        }
        Log.i("Before putInLocalDB", " hello");
        putInLocalDB(addList);
    }

    private void putInLocalDB(List<Quote> addList) {

        //int ItemId = addList.get(1).getItemId();
        //int quantity = addList.get(1).getItemId();
        String[] ItemsArray = {"Bread", "Butter", "Eggs", "Milk","Cheese","Chips","Jam","Orange Juice"};
        for(int i=0;i<addList.size();i++) {

            ListOperations listDBoperation;
            listDBoperation = new ListOperations(context);
            listDBoperation.open();
            ItemId = addList.get(i).getItemId();
            quant = addList.get(i).getQuantity();

            //change ItemsArray[3] to ItemsArray[ItemId]
            listDBoperation.addItem(ItemId, ItemsArray[ItemId], quant);

        }
    }

    private void addToServer(List<Quote> localDB, List<Quote> serverDB) {
        List<Quote> addList = new ArrayList<>();
        for(Quote localQ: localDB){
            boolean isFound  = false;
            for(Quote serverQ: serverDB){
                if(localQ.getItemId()==serverQ.getItemId()){
                    isFound = true;
                }
            }
         addList.add(localQ);
        }

        // Now got the list of Quotes which are there in local DB but there in ServerDB
        QuoteConnector qc = new QuoteConnector(0);
        for (Quote q:addList){
            try{
                //Log.d("vince SyncServer nt local:",""+q.getItemId());
                qc.insertQuote(q);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
