package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import bdeb.qc.ca.tp2_dev_mobile.R;

public class AjouterEnseignant extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_compte);
        setupToolbar();
    }

    /**
     * Cette méthode set la toolbar avec un onClick qui ferme l'activité
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarAjout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_accueil_admin_professor, menu);
        return true;
    }
}
