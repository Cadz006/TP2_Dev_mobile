package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import bdeb.qc.ca.tp2_dev_mobile.R;

public class AccueilAdminActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_admin);
        setToolbar();
    }

    /**
     * Cette méthode initialise les propriétés de la toolbar.
     */
    private void setToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarAdmin);
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
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    public void onClickEnseignant(View v)
    {
        Intent intent = new Intent(this, AjouterEnseignant.class);
        startActivity(intent);
    }
}
