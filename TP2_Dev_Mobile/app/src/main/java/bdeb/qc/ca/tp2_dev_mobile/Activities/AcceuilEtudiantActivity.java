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
    private RecyclerView recyclerView;
    private MetierAdapter metierAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String KEY_METIER = "bdeb.qc.ca.tp2_dev_mobile.AcceuilEtudiantActivity.KEY_QUESTIONS";
    public static final int EDIT_QUESTIONS_CODE = 46346;

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
        Intent intent = new Intent(this, QuestionListActivity.class);
        intent.putExtra(KEY_METIER, metierList.get(position));
        startActivityForResult(intent, EDIT_QUESTIONS_CODE);
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
