package com.example.connect3idriszahir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // here we are just connecting the file to xml


        EditText nameET = findViewById(R.id.nameET);
        Button luckBtn = findViewById(R.id.luckBtn);
        //so here we are just finding in the xml the button and edittext
        //name

        // when button is clicked, send the username to GameActivity
        luckBtn.setOnClickListener(v -> {
            String username = nameET.getText().toString();
            /* ok so now ofc we create a button for when its clicked
            and when a user types their name we can store their name inside
            string username and use it

             */

            /* here we created an intent and said we want to
            go into gameactivity in this first line
             */
            Intent intent = new Intent(WelcomeActivity.this, GameActivity.class);
            intent.putExtra("USERNAME", username);
            // here we attached data to the intent, that being the users username
            // then we can start the activity
            startActivity(intent);
        });
    }
}
