package bdeb.qc.ca.tp2_dev_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Metier implements Parcelable
{

    private String metierLetter;
    private int progress;
    private ArrayList<QuestionListItem> questions;

    public Metier(String metierLetter, int progress) {
        this.metierLetter = metierLetter;
        this.progress = progress;

        // pass this in arg instead. just for testing
        this.questions = new ArrayList<>();
        this.questions.add(new QuestionListItem("Test0 Test0 Test0 Test0 Test0 Test0 Test0 Test0 Test0 Test0 ?"));
        this.questions.add(new QuestionListItem("Test1 ?"));
        this.questions.add(new QuestionListItem("Test2 ?"));
        this.questions.add(new QuestionListItem("Test3 ?"));
        this.questions.add(new QuestionListItem("Test4 ?"));
        this.questions.add(new QuestionListItem("Test5 ?"));
        this.questions.add(new QuestionListItem("Test6 ?"));
        this.questions.add(new QuestionListItem("Test7 ?"));
        this.questions.add(new QuestionListItem("Test8 ?"));
    }

    protected Metier(Parcel in)
    {
        metierLetter = in.readString();
        progress = in.readInt();
        questions = in.readArrayList(QuestionListItem.class.getClassLoader());
    }

    public static final Creator<Metier> CREATOR = new Creator<Metier>()
    {
        @Override
        public Metier createFromParcel(Parcel in)
        {
            return new Metier(in);
        }

        @Override
        public Metier[] newArray(int size)
        {
            return new Metier[size];
        }
    };

    public ArrayList<QuestionListItem> getQuestions()
    {
        return questions;
    }

    public String getMetierLetter() {
        return metierLetter;
    }

    public void setMetierLetter(String metierLetter) {
        this.metierLetter = metierLetter;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(metierLetter);
        parcel.writeInt(progress);
        parcel.writeList(questions);
    }
}
