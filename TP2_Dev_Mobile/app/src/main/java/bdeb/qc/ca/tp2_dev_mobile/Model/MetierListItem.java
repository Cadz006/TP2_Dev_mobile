package bdeb.qc.ca.tp2_dev_mobile.Model;

import java.util.ArrayList;

public class MetierListItem
{
    private ArrayList<Integer> questions; // todo replace with questions
    private String title;

    public MetierListItem(ArrayList<Integer> questions, String title)
    {
        this.questions = questions;
        this.title = title;
    }

    public ArrayList<Integer> getQuestions()
    {
        return questions;
    }

    public String getTitle()
    {
        return title;
    }
}
