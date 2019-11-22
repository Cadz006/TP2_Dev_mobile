package bdeb.qc.ca.tp2_dev_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class Accueil_prof extends AppCompatActivity {

    private ArrayList<Etudiant> listClient;
    private RecyclerView recyclerView;
    private EtudiantAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public int positionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_prof);
        toolbarSetup();

    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuil);
        toolbar.setTitle(getResources().getString(R.string.toolBarAccProf));
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
