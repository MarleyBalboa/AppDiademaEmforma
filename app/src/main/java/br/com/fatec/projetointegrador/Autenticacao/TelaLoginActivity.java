package br.com.fatec.projetointegrador.Autenticacao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;


import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.Configuration.SessionManager;
import br.com.fatec.projetointegrador.Home;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Auth.LoginRequest;
import br.com.fatec.projetointegrador.Retrofit.Model.Auth.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaLoginActivity extends AppCompatActivity {
    private AppCompatEditText emailInput, passwordInput;
    private AppCompatButton loginButton;
    private TextView btnGoToRegister;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        emailInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextTextEmail);
        loginButton = findViewById(R.id.btnEntrar);
        btnGoToRegister = findViewById(R.id.btnCadastrese);

        loginButton.setOnClickListener(v -> loginUser());
        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, TelaCadastroActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        if (!validateInputs(email, password)) return;

        LoginRequest req = new LoginRequest(email, password);
        UsuarioApi api = new RetrofitClient()
                .getRetrofit()
                .create(UsuarioApi.class);

        api.login(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();
                    session.saveUserId(body.getId().longValue());
                    session.saveUserName(body.getNome());

                    Intent intent = new Intent(TelaLoginActivity.this, Home.class);
                    intent.putExtra("NOME_USUARIO", body.getNome());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TelaLoginActivity.this,
                            "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TelaLoginActivity.this,
                        "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

