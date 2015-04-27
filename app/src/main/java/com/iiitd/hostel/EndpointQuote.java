package com.iiitd.hostel;

/**
 * Created by vince on 4/19/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iiitd.hostel.backend.myApi.MyApi;
import com.iiitd.hostel.backend.quoteApi.QuoteApi;
import com.iiitd.hostel.backend.quoteApi.model.Quote;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EndpointQuote extends AsyncTask<Void, Void, List<Quote>> {
    private static QuoteApi myApiService = null;
    private Context context;

    EndpointQuote(Context context) {
        this.context = context;
    }

    @Override
    protected List<Quote> doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://concise-lambda-87311.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.listQuote().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Quote> result) {
        for (Quote q : result) {
            Toast.makeText(context, q.getWho() + " : " + q.getWhat(), Toast.LENGTH_LONG).show();
        }
    }
}