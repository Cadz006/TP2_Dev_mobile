package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bdeb.qc.ca.tp2_dev_mobile.Model.Etudiant;
import bdeb.qc.ca.tp2_dev_mobile.Adapters.EtudiantAdapter;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class AccueilProfActivity extends AppCompatActivity {

    private ArrayList<Etudiant> listEtudiant;
    private EtudiantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_prof);
        setupToolbar();
        addSampleDataToListEtudiant();
        createRecyclerView();
        addListenerToAdapter();
    }

    /**
     * Cette méthode initialise les propriétés de la toolbar.
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuilProf);
        toolbar.setTitle(getResources().getString(R.string.toolBarAccProf));
        setSupportActionBar(toolbar);
    }

    /**
     * Cette méthode ajoute des données dans la liste d'étudiants.
     * Pour l'instant, elle sert parce que nous n'arrivons pas à avoir accès à l'API.
     */
    private void addSampleDataToListEtudiant()
    {
        listEtudiant = new ArrayList<>();
        listEtudiant.add(new Etudiant("Cadieux", "Olivier", "ocadz@hotmail.ca", true, "", ""));
        listEtudiant.add(new Etudiant("De La Barrière", "Guillaume", "mightguy@hotmail.ca", true, "", ""));
        listEtudiant.add(new Etudiant("Phalakhone", "Nick", "nick@hotmail.ca", true, "", ""));
    }

    /**
     * Cette méthode initialise les propriétés du recycler view et du adapter.
     */
    private void createRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProf);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EtudiantAdapter(listEtudiant);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Cette méthode ajoute un événement au adapteur.
     * L'événement de l'étudiant démarre l'intent de l'étudiant (avec les 6 mots de METIER),
     * mais pour le professeur.
     */
    private void addListenerToAdapter()
    {
        adapter.setOnItemClickListener(new EtudiantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectionEtudiant(position);
            }
        });
    }

    /**
     * Cette méthode est appelée lorsque l'utilisateur clique sur un étudiant.
     * @param position La position de l'étudiant dans la liste.
     */
    public void selectionEtudiant(int position)
    {
        Intent intent = new Intent(this, AcceuilEtudiantActivity.class);
        intent.putExtra("IsProf", true);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_accueil_prof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.triAlpha:
                triAlpha();
                break;
            case R.id.triActCompl:
                finish();
                break;
            case R.id.logoutProf:
                finish();
                break;
        }
        return true;
    }

    /**
     * Cette méthode trie alphabétiquement la liste des étudiants par leur nom de famille.
     */
    public void triAlpha()
    {
        List<Etudiant> listEtudiantSorted = new ArrayList<>();
        Collections.sort(listEtudiant, new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant etudiant1, Etudiant etudiant2) {
                return etudiant1.getNom().compareTo(etudiant2.getNom());
            }
        });

        listEtudiantSorted.addAll(listEtudiant);
        listEtudiant = new ArrayList<>();
        listEtudiant.addAll(listEtudiantSorted);
    }
}
