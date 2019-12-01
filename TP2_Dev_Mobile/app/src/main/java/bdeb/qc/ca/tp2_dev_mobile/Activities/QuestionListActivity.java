package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Adapters.QuestionItemAdapter;
import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionListActivity extends AppCompatActivity
{
    private ArrayList<QuestionListItem> questions;
    private QuestionItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        addSampleQuestions();
        createRecyclerView();
        addListenerToAdapter();
    }

    private void addListenerToAdapter()
    {
        adapter.setListener(new QuestionItemAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int pos)
            {
                // todo start guillaume's app
            }
        });
    }

    private void createRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.questions_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new QuestionItemAdapter(questions);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void addSampleQuestions()
    {
        questions = new ArrayList<>();
        questions.add(new QuestionListItem("a sjhhjsa"));
        questions.add(new QuestionListItem("ddsSDDSSDhdeadasdsdssdjsa"));
        questions.add(new QuestionListItem("a sjdhhjsa"));
        questions.add(new QuestionListItem("a sjhahjsa"));
        questions.add(new QuestionListItem("a sjhsdshjsa"));
    }
}
