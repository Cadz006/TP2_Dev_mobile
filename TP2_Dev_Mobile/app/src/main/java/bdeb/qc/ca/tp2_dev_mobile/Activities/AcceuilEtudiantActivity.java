package bdeb.qc.ca.tp2_dev_mobile.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Model.Metier;
import bdeb.qc.ca.tp2_dev_mobile.Adapters.MetierAdapter;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class AcceuilEtudiantActivity extends AppCompatActivity {

    private ArrayList<Metier> metierList;
    private MetierAdapter metierAdapter;
    private boolean IsProf;

    public static final String KEY_METIER = "bdeb.qc.ca.tp2_dev_mobile.AcceuilEtudiantActivity.KEY_QUESTIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_etudiant_activity);
        setupToolbar();
        addSampleDataToMetierList();
        createRecyclerView();
        addListenerToAdapter();
        IsProf = getIntent().getBooleanExtra("IsProf", false);
    }

    /**
     * Cette méthode initialise les propriétés de la toolbar.
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuil);
        toolbar.setTitle(getResources().getString(R.string.toolbarTitle));
        setSupportActionBar(toolbar);
    }

    /**
     * Cette méthode ajoute des données dans la liste de métiers.
     * Pour l'instant, elle sert parce que nous n'arrivons pas à avoir accès à l'API.
     */
    private void addSampleDataToMetierList()
    {
        metierList = new ArrayList<>();
        metierList.add(new Metier("Machouin", 50));
        metierList.add(new Metier("Ewhatever", 70));
        metierList.add(new Metier("Torpinouche", 100));
        metierList.add(new Metier("Ipatente", 15));
        metierList.add(new Metier("Esti de BS", 0));
        metierList.add(new Metier("Rouyn-Noranda", 60));
    }

    /**
     * Cette méthode initialise les propriétés du recycler view et du adapter.
     */
    private void createRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recViewMetierProg);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        metierAdapter = new MetierAdapter(metierList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(metierAdapter);
    }

    /**
     * Cette méthode ajoute un événement au adapteur.
     * L'événement ouvre une intent qui contient la liste des questions pour un métier spécifique.
     */
    private void addListenerToAdapter()
    {
        metierAdapter.setOnItemClickListener(new MetierAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                openActivityMetierLetter(position);
            }
        });
    }

    /**
     * Cette méthode ouvre une intent qui contient la liste des questions pour un métier spécifique.
     * @param position La position du métier dans la liste de métiers.
     */
    public void openActivityMetierLetter(int position)
    {
        Intent intent = new Intent(this, QuestionListActivity.class);
        intent.putExtra(KEY_METIER, metierList.get(position));
        if (IsProf)
        {
            intent.putExtra("IsProf", true);
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.itemLogout)
        {
            finish();
        }
        return true;
    }
}
