package br.com.fatec.projetointegrador.CadastroLogin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;

import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.Especialidade;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.Papel;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroActivity extends AppCompatActivity {

    // Aqui iniciamos as variáveis que conterão os dados de cadastro do usuário
    // username, email, password, confirm password, telefone opcional e se é usuário comum ou profissional
    private AppCompatEditText usernameInput, emailInput, passwordInput, confirmPasswordInput, telephoneInput, roleInput;
    private AppCompatButton registerButton;
    private Spinner spinnerPapel, spinnerEspecialidade;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        initializeComponents();
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void initializeComponents() {
        usernameInput = findViewById(R.id.editTextTextUsername);
        emailInput = findViewById(R.id.editTextPassword);
        passwordInput = findViewById(R.id.editTextTextPassword);
        confirmPasswordInput = findViewById(R.id.editTextTextConfirmPassword);
        registerButton = findViewById(R.id.btnRegistrar);

        spinnerPapel = findViewById(R.id.spinner_EditRole);
        spinnerEspecialidade = findViewById(R.id.spinner_EditSpecialty);

        //Inicializa o spinner de papéis e especialidades
        setupSpinner();

        // Troca para a tela de login
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, TelaLoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        Papel selectedRole = (Papel) spinnerPapel.getSelectedItem();
        Especialidade selectedSpecialty = selectedRole == Papel.USUARIO_PROFISSIONAL ?
                (Especialidade) spinnerEspecialidade.getSelectedItem() : null;


        // Checa se o papel é usuário profissional, se for, ele cria com o campo especialidade, se não for, envia como nulo
        Usuario usuario;
        if (selectedRole == Papel.USUARIO_PROFISSIONAL) {
            usuario = new Usuario(username, email, password, selectedRole, selectedSpecialty);
        } else {
            usuario = new Usuario(username, email, password, selectedRole, null);
        }



        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        UsuarioApi usuarioApi = new RetrofitClient().getRetrofit().create(UsuarioApi.class);
        usuarioApi.criarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    createSuccessDialog(username).show();
                    clearFields();
                } else {
                    Toast.makeText(TelaCadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(TelaCadastroActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TelaCadastroActivity", "Erro: " + t.getMessage());
            }
        });

        Log.d("TelaCadastroActivity", "Enviando usuário: " + new Gson().toJson(usuario));
    }



    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private AlertDialog createSuccessDialog(String username) {
        return new AlertDialog.Builder(this)
                .setMessage("Cadastro realizado com sucesso!\nSeja bem-vindo(a) " + username + "!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    startActivity(new Intent(this, TelaLoginActivity.class));
                    finish();
                })
                .create();
    }

    private String removeAccents(String text) {
        String normalized = java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    private void setupSpinner() {
        ArrayAdapter<Papel> papelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Papel.values());
        papelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPapel.setAdapter(papelAdapter);

        ArrayAdapter<Especialidade> especialidadeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Especialidade.values());
        especialidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecialidade.setAdapter(especialidadeAdapter);

        // Inicialmente esconde o spinner de especialidades
        spinnerEspecialidade.setVisibility(View.GONE);

        // Mostra ou esconde baseado na seleção
        spinnerPapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Papel papelSelecionado = (Papel) parent.getItemAtPosition(position);
                if (papelSelecionado == Papel.USUARIO_PROFISSIONAL) {
                    spinnerEspecialidade.setVisibility(View.VISIBLE);
                } else {
                    spinnerEspecialidade.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }



    private void clearFields() {
        usernameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
        confirmPasswordInput.setText("");
    }
}
