package com.example.native17;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Toolbar mToolbar;
    TextView mResultTextView;
    EditText mInputEditText;
    AlertDialog mExceptionAlertDialog;
    AlertDialog mHelpAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        mResultTextView = findViewById(R.id.result_text);
        mInputEditText = findViewById(R.id.input_text);

        mExceptionAlertDialog = new AlertDialog.Builder(this)
                .setTitle("Error")
                //.setMessage("123")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();

        mHelpAlertDialog = (new AlertDialog.Builder(this))
                .setTitle("Help")
                .setMessage(getString(R.string.action_help_operation_list))
                .setIcon(android.R.drawable.ic_menu_help)
                .create();

        Button calculateButton = findViewById(R.id.calculate_button_text);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // [?} what if it calculate is too long?,
                //  maybe I should use like different threads for calculating
                // [?] maybe add (крутящююся штуку) to indicate that calculation is in progress,
                //  because long computations make cause freezes -> not very pleasant
                try {
                    String result = calculate(mInputEditText.getText().toString());
                    mResultTextView.setText(result);
                } catch (RuntimeException re) {
                    mExceptionAlertDialog.setMessage(re.getMessage());
                    mExceptionAlertDialog.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_help:
                mHelpAlertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public native String calculate(String input) throws RuntimeException;

}