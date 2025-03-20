package br.com.fatec.projetointegrador.CadastroLogin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroActivity extends AppCompatActivity {

    // Aqui iniciamos as variáveis que conterão os dados de cadastro do usuário
    // username, email, password, confirm password, telefone opcional e se é usuário comum ou profissional
    // e também cria uma variável para a seleção de Papel como roleSprinner e seleção de especialidade como specialtySpinner
    private AppCompatEditText usernameInput, emailInput, passwordInput, confirmPasswordInput, telephoneInput, roleInput;
    private AppCompatButton registerButton;
    private Spinner roleSpinner, specialtySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        initializeComponents();
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void initializeComponents() {
        usernameInput = findViewById(R.id.editTextTextUsername);
        emailInput = findViewById(R.id.editTextTextEmail);
        roleSpinner =  findViewById(R.id.spinner_EditRole);
        specialtySpinner = findViewById(R.id.spinner_EditSpecialty);
        passwordInput = findViewById(R.id.editTextTextPassword);
        confirmPasswordInput = findViewById(R.id.editTextTextConfirmPassword);
        registerButton = findViewById(R.id.btnRegistrar);

        //Inicializa o spinner de papéis e especialidades
        setupSpinner();
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        Usuario usuario = new Usuario(username, email, password);
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

    private void setupSpinner() {
        // Lista de papéis do usuário
        String[] roles = {"Usuário Comum", "Usuário Profissional", "Administrador"};

    // Lista de especialidades
        String[] specialties = {"Nutricionista", "Personal Trainer", "Psicólogo", "Outro"};

        ArrayAdapter<String> adapterRoles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapterRoles);

    // Inicialmente, escondemos o spinner de especialidades
        specialtySpinner.setVisibility(View.GONE);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = roles[position];

                // Se for "Usuário Profissional", mostramos o spinner de especialidade
                if ("Usuário Profissional".equals(selectedRole)) {
                    ArrayAdapter<String> adapterSpecialties = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, specialties);
                    adapterSpecialties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    specialtySpinner.setAdapter(adapterSpecialties);
                    specialtySpinner.setVisibility(View.VISIBLE);
                } else {
                    specialtySpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void clearFields() {
        usernameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
        confirmPasswordInput.setText("");
    }
}
