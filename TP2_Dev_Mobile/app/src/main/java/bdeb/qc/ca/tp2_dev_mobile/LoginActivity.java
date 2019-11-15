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
        etxtEmail = findViewById(R.id.textEmail);
        etxtPassword = findViewById(R.id.etxtPassword);

        toolbarSetup();
        btnListener(etxtEmail, etxtPassword);
    }

    private void btnListener(final EditText etEmail, final EditText etPassword) {
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError("Veuillez entrer un émail!");
                }
                else if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError("Veuillez entrer un mot de passe!");
                }
                else {
                    Intent login = new Intent(LoginActivity.this, AcceuilEtudiantActivity.class);
                    startActivity(login);
                }
            }
        });
    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.toolbarAcceuil);
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