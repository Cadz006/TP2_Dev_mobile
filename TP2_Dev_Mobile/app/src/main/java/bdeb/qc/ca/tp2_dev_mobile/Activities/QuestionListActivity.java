package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.AcceuilEtudiantActivity;
import bdeb.qc.ca.tp2_dev_mobile.Adapters.QuestionItemAdapter;
import bdeb.qc.ca.tp2_dev_mobile.Metier;
import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionListActivity extends AppCompatActivity
{
    private Metier metier;
    private String metierLetter;
    private QuestionItemAdapter adapter;

    public static final String KEY_QUESTION = "bdeb.qc.ca.tp2_dev_mobile.QuestionListActivity.KEY_QUESTION";
    public static final int EDIT_QUESTION_CODE = 71294;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        createToolbar();
        metier = getIntent().getParcelableExtra(AcceuilEtudiantActivity.KEY_METIER);
        //metierLetter = getIntent().getStringExtra( );
        createRecyclerView();
        addListenerToAdapter();
    }

    private void createToolbar()
    {
        Toolbar t = findViewById(R.id.questions_toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(metierLetter);
        t.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void addListenerToAdapter()
    {
        adapter.setListener(new QuestionItemAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int pos)
            {
                QuestionListItem question = metier.getQuestions().get(pos);
                Intent intent = new Intent(/*guillaume's class*/);
                intent.putExtra(KEY_QUESTION, question);
                startActivityForResult(intent, EDIT_QUESTION_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // receive data from guillaume's class
    }
}
