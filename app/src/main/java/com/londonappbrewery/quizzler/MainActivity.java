package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    Button mTrueButton, mFalseButton;
    TextView mQuestionTextView;
    int mIndex, mQuestion, mScore;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int)Math.ceil(100.0/mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking for previously saved instance state
        if(savedInstanceState != null){
            //In case there is a previously saved state, we are going
            // to fill our variables with that saved content.
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

            //But we still need to update our textview, which we've done at line 75.

        }else{

            mScore = 0;
            mIndex = 0;

        }

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        mScoreTextView = (TextView)findViewById(R.id.score);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        //Updating the textview in case of loading a saved instance state
        mScoreTextView.setText("Score: " + mScore + "/" + mQuestionBank.length);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(true);
                updateQuestion();

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);
                updateQuestion();

            }
        });

    }

    private void updateQuestion(){

        mIndex = (mIndex + 1) % mQuestionBank.length;

        if(mIndex == 0){
            //Here we user 'this' instead of 'getApplicationContext()'
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points.");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alert.show();

        }

        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score: " + mScore + "/" + mQuestionBank.length);

    }

    private void checkAnswer(boolean userSelection){

        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(userSelection == correctAnswer){

            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore++;

        }else{

            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        //Here we are saving the content of the variables to keep it in case the user minimize the app
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);

    }

}
