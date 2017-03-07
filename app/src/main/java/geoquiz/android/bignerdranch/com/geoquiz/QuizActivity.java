package geoquiz.android.bignerdranch.com.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class QuizActivity extends AppCompatActivity {



    private final static String TAG = "QuizActivity";
    private final static String INDEX_KEY = "index";
    private final static String IS_CHEATER_KEY = "com.bignerdranch.android.geoquiz.is_cheater";
    private final static int REQUEST_CODE_CHEAT = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;


    private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_ocean, true, false),
        new Question(R.string.question_mideast, false, false),
        new Question(R.string.question_africa, false, false),
        new Question(R.string.question_americas, true,false),
        new Question(R.string.question_asia, true, false),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;


    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                //mIsCheater = false;
                mIsCheater = mQuestionBank[mCurrentIndex].isCheatedTrue();

                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                else{
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }

                //mIsCheater = false;
                mIsCheater = mQuestionBank[mCurrentIndex].isCheatedTrue();

                updateQuestion();
            }
        });


        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });


        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(INDEX_KEY, 0);
            mIsCheater = savedInstanceState.getBoolean(IS_CHEATER_KEY,false);
        }


        updateQuestion();
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }



    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }



    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }


    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState(Bundle) called");
        savedInstanceState.putInt(INDEX_KEY, mCurrentIndex);
        savedInstanceState.putBoolean(IS_CHEATER_KEY, mIsCheater);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }

            mIsCheater = CheatActivity.wasAnswerShown(data);

            if(mIsCheater){
                mQuestionBank[mCurrentIndex].setCheatedTrue(mIsCheater);
            }
        }
    }



    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }



    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        }
        else{
            if(userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            }
            else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
