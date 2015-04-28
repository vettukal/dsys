package com.iiitd.hostel;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.iiitd.hostel.backend.quoteApi.QuoteApi;
import com.iiitd.hostel.backend.quoteApi.model.Quote;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by vince on 4/27/2015.
 */

public class QuoteConnector {
    private static QuoteApi myApiService = null;

    QuoteConnector(int server){
        Log.d("vince","doInbackground");
        if(myApiService == null) {  // Only do this once
            // QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
            //         .setRootUrl("https://dsys-project.appspot.com/_ah/api/");
            int test = server;// 0->checking from the GAE; 1-> local server.
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

        }

    }

    public List<Quote> listQuote() throws IOException {
        return myApiService.listQuote().execute().getItems();
    }

    public Quote insertQuote(Quote quote) throws Exception{
        return myApiService.insertQuote(quote).execute();
    }

    public Quote updateQuote(Quote quote) throws Exception {
        return myApiService.updateQuote(quote).execute();
    }
}
