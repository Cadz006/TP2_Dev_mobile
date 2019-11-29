package bdeb.qc.ca.tp2_dev_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class Accueil_prof extends AppCompatActivity {

    private ArrayList<Etudiant> listEtudiant;
    private RecyclerView recyclerView;
    private EtudiantAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public int positionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_prof);
        setup();

        adapter.setOnItemClickListener(new EtudiantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                notifyItemSelected(position);
            }
        });

    }

    private void setup() {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuil);
        toolbar.setTitle(getResources().getString(R.string.toolBarAccProf));
        setSupportActionBar(toolbar);

        listEtudiant = new ArrayList<>();

        recyclerView = findViewById(R.id.recViewMetierProg);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new EtudiantAdapter(listEtudiant);
    }

    public void notifyItemSelected(int position) {
        Log.v("RecyclerView", "" + position + " a été sélectionné: " + listEtudiant.get(position).getNom());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.triAlpha:
                finish();
                break;
            case R.id.triActCompl:
                finish();
                break;
        }
        return true;
    }
}
