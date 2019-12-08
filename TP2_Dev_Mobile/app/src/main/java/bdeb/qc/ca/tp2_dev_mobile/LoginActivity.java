package bdeb.qc.ca.tp2_dev_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etxtEmail;
    private EditText etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etxtEmail = findViewById(R.id.login_etxtEmail);
        etxtPassword = findViewById(R.id.login_etxtPassword);

        toolbarSetup();
        btnLoginListener(etxtEmail, etxtPassword);
        btnGoogleListener();
    }

    private void btnGoogleListener() {
        Button btnGoogle = findViewById(R.id.login_btnLoginGoogle);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void btnLoginListener(final EditText etEmail, final EditText etPassword) {
        Button btnLogin = findViewById(R.id.login_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError(getResources().getString(R.string.errorLoginEmail));
                }
                if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError(getResources().getString(R.string.errorLoginPassword));
                }
                else {
                    Intent login = null;
                    if(etxtEmail.getText().toString().equals("prof"))
                    {
                        login = new Intent(LoginActivity.this, Accueil_prof.class);
                    } else
                    {
                        login = new Intent(LoginActivity.this, AcceuilEtudiantActivity.class);
                    }
                    startActivity(login);
                }
            }
        });
    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.login_toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbarTitle));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        etxtEmail.setText("");
        etxtPassword.setText("");
    }
}
