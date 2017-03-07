package geoquiz.android.bignerdranch.com.geoquiz;

/**
 * Created by Neimad on 27/02/2017.
 */

public class Question
{



    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mCheatedTrue;



    public Question(int textResId, boolean answerTrue, boolean cheatedTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheatedTrue = cheatedTrue;
    }



    public int getTextResId()
    {
        return mTextResId;
    }



    public void setTextResId(int textResId)
    {
        mTextResId = textResId;
    }



    public boolean isAnswerTrue()
    {
        return mAnswerTrue;
    }



    public void setAnswerTrue(boolean answerTrue)
    {
        mAnswerTrue = answerTrue;
    }


    public boolean isCheatedTrue()
    {
        return mCheatedTrue;
    }



    public void setCheatedTrue(boolean cheatedTrue)
    {
        mCheatedTrue = cheatedTrue;
    }
}
