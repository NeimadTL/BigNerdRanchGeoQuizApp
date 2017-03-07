package geoquiz.android.bignerdranch.com.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {



    private final static String ANSWER_IS_TRUE_EXTRA = "com.bignerdranch.android.geoquiz.answer_is_true";
    private final static String ANSWER_SHOWN_EXTRA = "com.bignerdranch.android.geoquiz.answer_shown";



    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mShowAnswerButton;
    private TextView mApiLevelTextView;



    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(ANSWER_IS_TRUE_EXTRA, answerIsTrue);
        return  intent;
    }



    public static boolean wasAnswerShown(Intent result)
    {
        return result.getBooleanExtra(ANSWER_SHOWN_EXTRA, false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(ANSWER_IS_TRUE_EXTRA, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mApiLevelTextView = (TextView) findViewById(R.id.api_level_text_view);
        mApiLevelTextView.setText("API level " + String.valueOf(Build.VERSION.SDK_INT));

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer();
                setAnswerShownResult(true);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    int cx = mShowAnswerButton.getWidth() / 2 ;
                    int cy = mShowAnswerButton.getHeight() / 2 ;
                    float radius = mShowAnswerButton.getWidth();
                    Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,cx,cy, radius,0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    animator.start();
                }
                else{
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });


        if(savedInstanceState != null){
            mAnswerIsTrue = savedInstanceState.getBoolean(ANSWER_IS_TRUE_EXTRA, false);
            updateAnswer();
        }
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(ANSWER_IS_TRUE_EXTRA, mAnswerIsTrue);
    }



    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(ANSWER_SHOWN_EXTRA, isAnswerShown);
        setResult(RESULT_OK, data);
    }



    private void updateAnswer()
    {
        if(mAnswerIsTrue){
            mAnswerTextView.setText(R.string.true_button);
        }
        else{
            mAnswerTextView.setText(R.string.false_button);
        }
    }
}
