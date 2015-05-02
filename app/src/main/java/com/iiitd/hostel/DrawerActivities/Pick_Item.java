package com.iiitd.hostel.DrawerActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iiitd.hostel.Database.ListDetails;
import com.iiitd.hostel.Database.ListOperations;
import com.iiitd.hostel.R;

import java.util.List;

public class Pick_Item extends ActionBarActivity {

    Context context;
    Button btnBread,btnButter,btnMilk,btnEggs,btnCheese;
    Button btnminusBread,btnminusButter,btnminusEggs,btnminusMilk,btnminusCheese;
    EditText editBread, editButter, editMilk, editEggs, editCheese;
    private int quantbread,quantmilk,quantbutter,quanteggs,quantcheese;
    private int[] Quant;
    long tsLong;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick__item);
        context = getApplicationContext();
        editBread = (EditText) findViewById(R.id.editBread);
        StoreValue();

    }


    public void StoreValue() {

        //Quant = new int[20];

        ListOperations lstDBoperation;
        lstDBoperation = new ListOperations(getApplicationContext());
        lstDBoperation.open();
        List<ListDetails> list = lstDBoperation.getAllItems();

        editBread = (EditText) findViewById(R.id.editBread);
        editEggs = (EditText) findViewById(R.id.editEggs);
        editButter = (EditText) findViewById(R.id.editButter);
        editMilk = (EditText) findViewById(R.id.editMilk);
        editCheese = (EditText) findViewById(R.id.editCheese);

        for (int j = 1; j <= 5; j++){
            if (list.size() == j) {
                for(int k=0;k<j;k++) {
                    if (list.get(k).getItem_Id() == 0) {
                        editBread.setText(String.valueOf(list.get(k).getQuantity()));

                    }
                    if (list.get(k).getItem_Id() == 1) {
                        editButter.setText(String.valueOf(list.get(k).getQuantity()));

                    }
                    if (list.get(k).getItem_Id() == 2) {
                        editEggs.setText(String.valueOf(list.get(k).getQuantity()));

                    }
                    if (list.get(k).getItem_Id() == 3) {
                        editMilk.setText(String.valueOf(list.get(k).getQuantity()));

                    }
                    if (list.get(k).getItem_Id() == 4) {
                        editCheese.setText(String.valueOf(list.get(k).getQuantity()));

                    }
                }
            }
        }

        btnBread = (Button) findViewById(R.id.btnBread);
        btnBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plus(editBread);

            }
        });
        btnButter = (Button) findViewById(R.id.btnButter);
        btnButter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                plus(editButter);

            }
        });

        btnMilk = (Button) findViewById(R.id.btnMilk);
        btnMilk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                plus(editMilk);

            }
        });

        btnEggs = (Button) findViewById(R.id.btnEggs);
        btnEggs.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                plus(editEggs);

            }
        });
        btnCheese = (Button) findViewById(R.id.btnCheese);
        btnCheese.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                plus(editCheese);

            }
        });

        btnminusBread = (Button) findViewById(R.id.btnminusBread);
        btnminusBread.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                minus(editBread);

            }
        });
        btnminusButter = (Button) findViewById(R.id.btnminusButter);
        btnminusButter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                minus(editButter);

            }
        });
        btnminusMilk = (Button) findViewById(R.id.btnminusMilk);
        btnminusMilk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                minus(editMilk);

            }
        });
        btnminusEggs = (Button) findViewById(R.id.btnminusEggs);
        btnminusEggs.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                minus(editEggs);

            }
        });
        btnminusCheese = (Button) findViewById(R.id.btnminusCheese);
        btnminusCheese.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                minus(editCheese);

            }
        });
        lstDBoperation.close();

       /* quantbread = Integer.parseInt(editBread.getText().toString());
        Quant[0] = quantbread;
        quantbutter = Integer.parseInt(editButter.getText().toString());
        Quant[1] = quantbutter;
        quanteggs = Integer.parseInt(editEggs.getText().toString());
        Quant[2] = quanteggs;
        quantmilk = Integer.parseInt(editMilk.getText().toString());
        Quant[3] = quantmilk;*/
    }

    public void plus(EditText editText){

        int currentValue;
        currentValue = Integer.parseInt(editText.getText().toString());
        currentValue = currentValue + 1;
        System.out.println(currentValue);

        editText.setText(String.valueOf(currentValue));

    }

    public void minus(EditText editText){

        int currentValue;
        currentValue = Integer.parseInt(editText.getText().toString());

        if(currentValue!=0){
            currentValue = currentValue - 1;
        }
        editText.setText(String.valueOf(currentValue));

    }

    public void AddtoList(View v){


        //StoreValue();
        Quant = new int[20];
        quantbread = Integer.parseInt(editBread.getText().toString());
        Quant[0] = quantbread;
        quantbutter = Integer.parseInt(editButter.getText().toString());
        Quant[1] = quantbutter;
        quanteggs = Integer.parseInt(editEggs.getText().toString());
        Quant[2] = quanteggs;
        quantmilk = Integer.parseInt(editMilk.getText().toString());
        Quant[3] = quantmilk;
        quantcheese = Integer.parseInt(editCheese.getText().toString());
        Quant[4] = quantcheese;

        String[] ItemsArray = {"Bread", "Butter", "Eggs", "Milk","Cheese"};
        ListOperations listDBoperation;
        listDBoperation = new ListOperations(context);
        listDBoperation.open();
        tsLong = System.currentTimeMillis()/1000;
        for(i=0;i<5;i++) {

            if(Quant[i]>0) {
                Log.i("timestamp", String.valueOf(tsLong));
                listDBoperation.addItem(i,ItemsArray[i],tsLong,Quant[i]);
            }

        }

        listDBoperation.close();
        Intent itemlist = new Intent(this, ItemList.class);
        startActivity(itemlist);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick__item, menu);
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
