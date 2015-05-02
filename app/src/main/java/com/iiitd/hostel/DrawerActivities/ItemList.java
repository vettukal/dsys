package com.iiitd.hostel.DrawerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iiitd.hostel.Database.ListDetails;
import com.iiitd.hostel.Database.ListOperations;
import com.iiitd.hostel.R;
import com.iiitd.hostel.SyncServer;

import java.util.List;


public class ItemList extends ActionBarActivity {

    ListView lstitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        lstitems = (ListView) findViewById(R.id.list_item);
        updateUI();

    }

    @Override
    protected void onRestart() {
        updateUI();
        super.onRestart();
    }

    public List listFromDB(){

        ListOperations lstDBoperation;
        lstDBoperation = new ListOperations(getApplicationContext());
        lstDBoperation.open();
        List<ListDetails> list = lstDBoperation.getAllItems();

        return list;

    }

    public void Add_Items(View v){

        Intent pickitem = new Intent(this, Pick_Item.class);
        startActivity(pickitem);

    }

    @Override
    protected void onStart() {
        updateUI();
        super.onStart();
    }

    public void Sync(View v){

        new SyncServer(this).execute(); //new EndpointQuote(this).execute();
        Intent itemlist = new Intent(this, ItemList.class);
        startActivity(itemlist);
        updateUI();


    }

    public void updateUI() {

        List<ListDetails> list = listFromDB();
        ListDetails [] objs= new ListDetails[list.size()];
        for(int i=0;i<list.size();i++ ){

            objs[i] = list.get(i);

        }
        ArrayAdapter<ListDetails> adapter = new ArrayAdapter<ListDetails>(this, android.R.layout.simple_list_item_1, objs);
        lstitems.setAdapter(adapter);

    }

    @Override
    protected void onResume() {

        updateUI();
        super.onResume();


    }

    @Override
    protected void onPostResume() {
        updateUI();
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
