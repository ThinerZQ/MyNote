package com.example.zq.imagebrowser;



import android.annotation.TargetApi;
import android.app.Activity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;


public class ActionBarTestActivity extends Activity {

   private android.app.ActionBar actionBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_test);

        actionBar = getActionBar();


        actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
