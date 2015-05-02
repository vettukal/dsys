package com.iiitd.hostel.Database;

import android.content.Context;

import com.iiitd.hostel.backend.quoteApi.model.Quote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruchika on 4/28/15.
 */
public class LocalDatabase {
    Context context;


    public List<Quote> getLocalQuotes(Context context) {

        ListOperations lstDBoperation;
        lstDBoperation = new ListOperations(context);
        lstDBoperation.open();
        List<ListDetails> list = lstDBoperation.getAllItems();

        List<Quote> listquote = new ArrayList<Quote>();
        for(ListDetails ld : list){

            Quote q = new Quote();
            q.setItemId(ld.getItem_Id());
            q.setQuantity(ld.getQuantity());
            q.setTimeStamp(ld.getTimestamp());
            listquote.add(q);

        }
        return listquote;


    }

}
