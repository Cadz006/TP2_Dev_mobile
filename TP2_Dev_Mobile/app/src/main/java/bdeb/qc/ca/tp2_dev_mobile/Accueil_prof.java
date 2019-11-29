package bdeb.qc.ca.tp2_dev_mobile;

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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Accueil_prof extends AppCompatActivity {

    private ArrayList<Etudiant>             listEtudiant;
    private RecyclerView                    recyclerView;
    private EtudiantAdapter                 adapter;
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

        listEtudiant.add(etudiant1);
        listEtudiant.add(etudiant2);
        listEtudiant.add(etudiant3);

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
        intent.putExtra("nom", etudiant.getNom());
        intent.putExtra("prenom", etudiant.getPrenom());
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
        int i,j;
        Collection<String> listNom = new TreeSet<>(Collator.getInstance());

        for (Etudiant etudiant: listEtudiant) {
            listNom.add(etudiant.getNom());
        }

    }
}
