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

    private ArrayList<Etudiant>             listEtudiant;
    private RecyclerView                    recyclerView;
    private EtudiantAdapter adapter;
    private RecyclerView.LayoutManager      layoutManager;
    private Etudiant                        etudiant;
    public int                              positionEtudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_prof);
        setup();



        adapter.setOnItemClickListener(new EtudiantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectionEtudiant(position);
            }
        });

    }

    private void setup() {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuilProf);
        String str = getResources().getString(R.string.toolBarAccProf);
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);


        listEtudiant = new ArrayList<>();

        Etudiant etudiant1 = new Etudiant("Cadieux", "Olivier", "ocadz@hotmail.ca", true, "", "");
        Etudiant etudiant2 = new Etudiant("De La Barrière", "Guillaume", "mightguy@hotmail.ca", true, "", "");
        Etudiant etudiant3 = new Etudiant("Phalakhone", "Nick", "nick@hotmail.ca", true, "", "");

        listEtudiant.add(etudiant2);
        listEtudiant.add(etudiant3);
        listEtudiant.add(etudiant1);

        recyclerView = findViewById(R.id.recyclerViewProf);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new EtudiantAdapter(listEtudiant);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void selectionEtudiant(int position){
        Etudiant etudiant = listEtudiant.get(position);
        positionEtudiant = position;
        Intent intent = new Intent(this, AcceuilEtudiantActivity.class);
        intent.putExtra("IsProf", true);
        startActivityForResult(intent, 2);
    }

    public void notifyItemSelected(int position) {
        Log.v("RecyclerView", "" + position + " a été sélectionné: " + listEtudiant.get(position).getNom());
        selectionEtudiant(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_accueil_prof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.triAlpha:
                triAlpha();
                break;
            case R.id.triActCompl:
                finish();
                break;
        }
        return true;
    }

    public void triAlpha(){
        List<Etudiant> listEtudiantSorted = new ArrayList<>();
        Collections.sort(listEtudiant, new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant etudiant1, Etudiant etudiant2) {
                return etudiant1.getNom().compareTo(etudiant2.getNom());
            }
        });
        for (Etudiant etudiant : listEtudiant){
            listEtudiantSorted.add(etudiant);
        }
        listEtudiant = new ArrayList<>();
        for (Etudiant etudiant : listEtudiantSorted){
            listEtudiant.add(etudiant);
        }

    }
}
