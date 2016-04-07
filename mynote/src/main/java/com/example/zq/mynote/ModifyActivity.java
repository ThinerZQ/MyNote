package com.example.zq.mynote;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;


public class ModifyActivity extends Activity {

    public final static int RESULT_CODE = 1;
    public String note_id;
    private EditText editText;
    private  TextView showNoteTimeTextView ;
    private String noteTime;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        note_id = bundle.getString("id");


        noteTime=bundle.getString("noteTime");

        String noteText = bundle.getString("noteText");
        editText = (EditText) findViewById(R.id.et_modify);
        showNoteTimeTextView = (TextView) findViewById(R.id.showNoteTime);
        editText.setText(noteText);
        editText.setSelection(noteText.length());


        Log.i("ModifyAcitvity",noteTime);
        showNoteTimeTextView.setText(noteTime);



        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            editText = (EditText) findViewById(R.id.et_modify);

            String str = editText.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("id", note_id);
            intent.putExtra("noteText", str);

            setResult(RESULT_CODE, intent);
            this.finish();
        }
        return false;
    }
}
