package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bdeb.qc.ca.tp2_dev_mobile.Database.DatabaseAPI;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class LoginActivity extends AppCompatActivity
{
    private EditText etxtEmail;
    private EditText etxtPassword;

    DatabaseAPI db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etxtEmail = findViewById(R.id.login_etxtEmail);
        etxtPassword = findViewById(R.id.login_etxtPassword);
        setupToolbar();
    }

    public void onGoogleButtonClick(View v)
    {

    }

    public void onLoginClick(View v)
    {
        boolean badConnection = false;
        if (etxtEmail.getText().toString().trim().isEmpty())
        {
            etxtEmail.setError(getResources().getString(R.string.errorLoginEmail));
            badConnection = true;
        }
        if (etxtPassword.getText().toString().trim().isEmpty())
        {
            etxtPassword.setError(getResources().getString(R.string.errorLoginPassword));
            badConnection = true;
        }
        if (badConnection)
        {
            return;
        }
        loginUser();
    }

    /**
     * Cette méthode permet de différencier entre le login d'un prof, de l'admin ou de l'étudiant
     */
    private void loginUser()
    {
        Intent login = null;
        switch (etxtEmail.getText().toString())
        {
            case "prof":
                login = new Intent(LoginActivity.this, AccueilProfActivity.class);
                break;
            case "admin":
                login = new Intent(LoginActivity.this, AccueilAdminActivity.class);
                break;
            default:
                login = new Intent(LoginActivity.this, AcceuilEtudiantActivity.class);
                break;
        }
        startActivity(login);
    }

    /**
     * Cette méthode set la toolbar
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.login_toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbarTitle));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        etxtEmail.setText("");
        etxtPassword.setText("");
    }
}
