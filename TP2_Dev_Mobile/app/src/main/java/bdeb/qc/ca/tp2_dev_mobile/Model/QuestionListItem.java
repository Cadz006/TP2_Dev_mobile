package bdeb.qc.ca.tp2_dev_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionListItem implements Parcelable
{
    private String question;

    public QuestionListItem(String question)
    {
        this.question = question;
    }

    protected QuestionListItem(Parcel in)
    {
        question = in.readString();
    }

    public static final Creator<QuestionListItem> CREATOR = new Creator<QuestionListItem>()
    {
        @Override
        public QuestionListItem createFromParcel(Parcel in)
        {
            return new QuestionListItem(in);
        }

        @Override
        public QuestionListItem[] newArray(int size)
        {
            return new QuestionListItem[size];
        }
    };

    public String getQuestion()
    {
        return question;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(question);
    }
}
