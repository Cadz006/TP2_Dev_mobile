package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import bdeb.qc.ca.tp2_dev_mobile.Adapters.QuestionItemAdapter;
import bdeb.qc.ca.tp2_dev_mobile.Model.Metier;
import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionListActivity extends AppCompatActivity
{
    private Metier metier;
    private QuestionItemAdapter adapter;
    private boolean IsProf;

    public static final String KEY_QUESTION = "bdeb.qc.ca.tp2_dev_mobile.QuestionListActivity.KEY_QUESTION";
    public static final int EDIT_QUESTION_CODE = 794;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        metier = getIntent().getParcelableExtra(AcceuilEtudiantActivity.KEY_METIER);
        setupToolbar();
        createRecyclerView();
        addListenerToAdapter();
        IsProf = getIntent().getBooleanExtra("IsProf", false);
    }

    /**
     * Cette méthode set la toolbar avec un onClick qui ferme l'activité
     */
    private void setupToolbar()
    {
        Toolbar t = findViewById(R.id.questions_toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(metier.getMetierLetter());
        t.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    /**
     * Cette méthode permet à l'utilisateur d'accèder à la page de réponse de l'étudiant pour la
     * question appuyé
     */
    private void addListenerToAdapter()
    {
        adapter.setListener(new QuestionItemAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int pos)
            {
                QuestionListItem question = metier.getQuestions().get(pos);
                Intent intent = new Intent(QuestionListActivity.this, QuestionEtudiantActivity.class);
                intent.putExtra(KEY_QUESTION, question);
                if(IsProf) { intent.putExtra("IsProf", true); }
                startActivity(intent);
            }
        });
    }

    /**
     * Cette méthode setup la création du recyclerView ainsi que l'adapter
     */
    private void createRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.questions_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new QuestionItemAdapter(metier.getQuestions());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
