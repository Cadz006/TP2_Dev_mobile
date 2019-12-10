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
        btnLoginListener(etxtEmail, etxtPassword);
        btnGoogleListener();
    }

    /**
     * Cette méthode permet de se logger avec le bouton google
     */
    private void btnGoogleListener()
    {
        Button btnGoogle = findViewById(R.id.login_btnLoginGoogle);

        btnGoogle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    /**
     * Cette méthode permet à l'utilisateur de se logger.
     * Si les paramètres sont vides, il y a un message d'erreur.
     * @param etEmail
     * @param etPassword
     */
    private void btnLoginListener(final EditText etEmail, final EditText etPassword)
    {
        Button btnLogin = findViewById(R.id.login_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (etEmail.getText().toString().trim().isEmpty())
                {
                    etEmail.setError(getResources().getString(R.string.errorLoginEmail));
                }
                if (etPassword.getText().toString().trim().isEmpty())
                {
                    etPassword.setError(getResources().getString(R.string.errorLoginPassword));
                }
                else
                {
                    loginUser();
                }
            }
        });
    }

    /**
     * Cette méthode permet de différencier entre le login d'un prof, de l'admin ou de l'étudiant
     */
    private void loginUser()
    {
        Intent login = null;
        if(etxtEmail.getText().toString().equals("prof"))
        {
            login = new Intent(LoginActivity.this, AccueilProfActivity.class);
        }
        else if(etxtEmail.getText().toString().equals("admin"))
        {
            login = new Intent(LoginActivity.this, AccueilAdminActivity.class);
        }
        else
        {
            login = new Intent(LoginActivity.this, AcceuilEtudiantActivity.class);
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
