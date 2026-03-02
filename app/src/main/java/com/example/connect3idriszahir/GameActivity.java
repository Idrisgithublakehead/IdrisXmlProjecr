package com.example.connect3idriszahir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    // 9 buttons for the board
    Button[] cells = new Button[9];
    // here we are creating an array of 9 Button objects.
    //These correspond to your 9 XML buttons, b0 → b8.

    int winStreak = 0;
    //win stream is set to 0 rn ofc later we update that by doing ++
    boolean gameOver = false;
    //update this when the user wins the game so its true and game ends


    TextView scoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String username = getIntent().getStringExtra("USERNAME");
        /* Right above here we are sending an intent that is in our
        welcome file , and we are grabbing whatever username the user typed
        and putting it into a string called username
         */

        TextView goodLuckTV = findViewById(R.id.goodLuckTV);
        goodLuckTV.setText(username + " Good Luck!!");
        /* now after we grab our username from the user, remember in our
        activity welcome xml file we declared the textview into a id called
        goodlucktv so now we are changing that id to show the username that the user
        typed plus the goodluck message
         */

        // ok so here we go inside our xml file find score tv
        //  and just give it to scoretv so we can use later
        scoreTV = findViewById(R.id.scoreTV);
        updateScore();
        // so when the screen first loads the win streak is 0
        //show the below method to explain this

        // connect share and back buttons so we can use
        Button shareBtn = findViewById(R.id.shareBtn);
        Button backBtn = findViewById(R.id.backBtn);

        // connect board buttons
        int[] ids = {
                // so every ID IN ANDROID IS ACTUALLY A INT THEREFORE THESE ARE ALL INTS
                R.id.b0, R.id.b1, R.id.b2,
                R.id.b3, R.id.b4, R.id.b5,
                R.id.b6, R.id.b7, R.id.b8
                // SO BASICCALLY now int[1] holds r.id.bo , etcetc

        };

        /* ok so now we can just create a loop for the 9 huttons
        that runs 9 times, below here cells[i] as it runs
        9 times it will take each value of the button as it goes
        because we did findviewby, so basically cells[1]-cells[9]

         */
        for (int i = 0; i < 9; i++) {
            cells[i] = findViewById(ids[i]);

            final int index = i;
            cells[i].setOnClickListener(v -> handlePlayerMove(index));
            // ok so right here above, we created a variable called index
            /* and just set it to i now we can use it inside
            with a lambda which creates a final copy of i
             */
        }

        // ok so here we grab our back button we made earlier
        /* and after grabing it we can call a built in function
        which is called finish and this brings the user back to the previous

         */
        backBtn.setOnClickListener(v -> finish());

        // share button
        shareBtn.setOnClickListener(v -> {
            String shareText = username + " score is " + winStreak;
            // just creates a string with username + winstreak
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            /*ok so the line above is making a new intent
            however this intent is actually a action send
            which tells android i wanna send something/share
             */
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            /* now this line tells the intent what i wanna share is
            the sharetext variable we declared earlier.
             */
            sendIntent.setType("text/plain");
            // type is plain text

            startActivity(Intent.createChooser(sendIntent, "Share Score"));
            /* ok now this final line does 2 things it
            actually runs the intent, and allows the user to chose
            what they wanna share their text in, share score is what will pop up
            and the send intent variable is what they can send
             */
        });
    }


    // ok so this method will run when player clicks a cell
    // this method links back to our previous code at the top
    /* so basically when the user for example clicks button 4
    4, is palced into handplayermove(4)
     */
    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // this line actually prevenets the user or app chosing a
        // box thats taken, If yes its take don’t allow clicking again.
        // the return stops it
        if (!cells[index].getText().toString().isEmpty()) return;


        cells[index].setText("X");
        // ok so right here whatver cell the user clicks in the array cells
        // that cell gets the value x put into it


        // so this method below actually checks if the player won
        //we call the method below check winner, passing a pramaeter too
        // if the user DID Win, then we run the update score method
        // and we call the resetboard method

        // if the user doesnt win, then outside see it says just run app move method
        if (checkWinner("X")) {
            winStreak++;
            updateScore();
            resetBoard();
            return;
        }

        // app move
        appMove();
    }






    private void appMove() {

        for (int i = 0; i < 9; i++) {
            /* so this code below places an 0 in the box
            the app chose ONLY IF ITS EMPTY
             */
            if (cells[i].getText().toString().isEmpty()) {
                cells[i].setText("O");
                break;
            }
        }

        if (checkWinner("O")) {
            gameOver = true;
            // set gameover as true so the game ends
            //now handplayergame method doesnt run
            scoreTV.setText("Final streak: " + winStreak);
            disableBoard();
            //call disabole method for board
        }
    }
















    // check winning combinations, METHOD, boolean, takes a parameter
    private boolean checkWinner(String symbol) {
        int[][] wins = {
                {0,1,2}, {3,4,5}, {6,7,8}, // rows
                {0,3,6}, {1,4,7}, {2,5,8}, // cols
                {0,4,8}, {2,4,6}           // diagonals
        };

        // Loop through every possible winning combination and check if all three
// corresponding board positions match the given symbol (X or O).
// If any combination matches, return true to indicate a win.

        for (int[] w : wins) {
            if (cells[w[0]].getText().toString().equals(symbol) &&
                    cells[w[1]].getText().toString().equals(symbol) &&
                    cells[w[2]].getText().toString().equals(symbol)) {
                return true;
                //once return true is sent the check winner method
                //that gets called in other methods that turns into true
                // and the player or app wins
            }
        }
        return false;
        // if none matches then the game continues
    }







    // update streak display
    private void updateScore() {
        scoreTV.setText("Win Streak: " + winStreak);
    }










    // clear board after player wins
    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            cells[i].setText("");
        }
    }








    // disable board when app wins
    private void disableBoard() {
        for (int i = 0; i < 9; i++) {
            cells[i].setEnabled(false);
        }
    }
}
