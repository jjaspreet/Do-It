package com.jassi.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.jassi.myapplication.R;

public class AddingEditNoteActivity extends AppCompatActivity {
    public static final String  EXTRA_ID=
            " com.jassi.myapplication.EXTRA_ID";
    public static final String  EXTRA_TITLE=
            " com.jassi.myapplication.EXTRA_TITLE";
    public static final String  EXTRA_DESCRIPTION=
            " com.jassi.myapplication.EXTRA_DESCRIPTION";
    public static final String  EXTRA_PRIORITY=
            " com.jassi.myapplication.EXTRA_PRIORITY";
 private  EditText edit1;
 private EditText edit2;
 NumberPicker numberp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
         edit1 = findViewById(R.id.edit_title);
         edit2 = findViewById(R.id.edit_description);
         numberp = findViewById(R.id.priority_picker);
         numberp.setMinValue(1);
         numberp.setMaxValue(10);

         getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
         Intent intent= getIntent();
            if (intent.hasExtra(EXTRA_ID)){
                setTitle("EDIT NOTE");
                edit1.setText(intent.getStringExtra(EXTRA_TITLE));
                edit2.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                numberp.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));


        }else{
         setTitle("Add Note"); }
    }
     private void saveNote(){
        String s1 = edit1.getText().toString();
        String s2 = edit2.getText().toString();
        int priority = numberp.getValue();
        if (s1 .trim().isEmpty() || s2.trim().isEmpty()){
            Toast.makeText(this, "Please Insert Title And Description Correctly", Toast.LENGTH_SHORT).show();
            return;
        }
         Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, s1);
        data.putExtra(EXTRA_DESCRIPTION, s2);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }


         setResult(RESULT_OK,data);
         finish();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflator = getMenuInflater();
        menuinflator.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu :
            saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
