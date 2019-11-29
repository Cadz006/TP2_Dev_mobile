package bdeb.qc.ca.tp2_dev_mobile;

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

public class AcceuilEtudiantActivity extends AppCompatActivity {

    private ArrayList<Metier> metierList;
    private RecyclerView recyclerView;
    private MetierAdapter metierAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_etudiant_activity);

        toolbarSetup();

        metierList = new ArrayList<>();

        metierList.add(new Metier("Machouin", 50));
        metierList.add(new Metier("Ewhatever", 70));
        metierList.add(new Metier("Torpinouche", 100));
        metierList.add(new Metier("Ipatente", 15));
        metierList.add(new Metier("Esti de BS", 0));
        metierList.add(new Metier("Rouyn-Noranda", 60));

        recyclerView = findViewById(R.id.recViewMetierProg);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        metierAdapter = new MetierAdapter(metierList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(metierAdapter);

        metierAdapter.setOnItemClickListener(new MetierAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openActivityMetierLetter(position);
            }
        });
    }

    public void openActivityMetierLetter(int position) {
        
    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuil);
        toolbar.setTitle(getResources().getString(R.string.toolbarTitle));
        setSupportActionBar(toolbar);
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
            case R.id.itemLogout:
                finish();
                break;
        }
        return true;
    }
}
