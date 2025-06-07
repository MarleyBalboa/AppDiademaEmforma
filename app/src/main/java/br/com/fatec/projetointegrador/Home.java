package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.fatec.projetointegrador.CadastroLogin.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Fragments.HomeFragment;
import br.com.fatec.projetointegrador.Fragments.PerfilFragment;
import br.com.fatec.projetointegrador.Fragments.ServicosFragment;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_fragment);

        // Deixa os ícones da status bar escuros (preto)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // Define a cor da barra de status como branca
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Carrega o fragmento inicial apenas na primeira criação
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == R.id.telainicio) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.telaservicos) {
                selectedFragment = new ServicosFragment();
            } else if (itemId == R.id.telaagendar) {
                startActivity(new Intent(Home.this, TelaAgendarActivity.class));
                return true;
            } else if (itemId == R.id.telaperfil) {
                selectedFragment = new PerfilFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

    private void showToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
