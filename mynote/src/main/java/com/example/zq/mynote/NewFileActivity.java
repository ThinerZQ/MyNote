package com.example.zq.mynote;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.EditText;


public class NewFileActivity extends Activity {

    public final static int RESULT_CODE = 1;
    private EditText editText;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_file);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("newFile");
        System.out.print(str);




        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            editText = (EditText) findViewById(R.id.editText);

            String str = editText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("noteText", str);
            setResult(RESULT_CODE, intent);
            this.finish();
        }
        return false;
    }

}
