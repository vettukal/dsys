package com.iiitd.hostel;

/**
 * Created by vince on 4/19/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

    public EndpointQuote(Context context) {
        this.context = context;
    }

    @Override
    protected List<Quote> doInBackground(Void... params) {
        Log.d("vince","doInbackground");
        if(myApiService == null) {  // Only do this once
           // QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
           //         .setRootUrl("https://dsys-project.appspot.com/_ah/api/");
            int test = 0;// 0->checking from the GAE; 1-> local server.
            QuoteApi.Builder builder = null;
            if(test==0){
                builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://dsys-project.appspot.com/_ah/api/");
            }
            else{
                builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
            }

            // end options for devappserver

            myApiService = builder.build();

            // adding the extra quote to the system.

            // ending the experiment.
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
            Toast.makeText(context, q.getWho() + " : " + q.getWhat() + " : " + q.getItemId() + " : " + q.getQuantity(), Toast.LENGTH_LONG).show();
        }
    }
}