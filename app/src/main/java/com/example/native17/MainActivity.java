package com.example.native17;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultTextView = findViewById(R.id.result_text);


        final EditText inputEditText = findViewById(R.id.input_text);


        Button calculateButton = findViewById(R.id.calculate_button_text);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // [?} what if it calculate is too long?,
                //  maybe I though use like different threads for calculating
                String result = calculate(inputEditText.getText().toString());
                resultTextView.setText(result);
            }
        });

    }

    public native String calculate(String input);
}
