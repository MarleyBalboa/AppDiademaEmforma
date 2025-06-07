package br.com.fatec.projetointegrador.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Adapter.CarrosselAdapter;
import br.com.fatec.projetointegrador.R;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private CarrosselAdapter adapter;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText searchHome = view.findViewById(R.id.et_search);

        viewPager2 = view.findViewById(R.id.carrosselViewPager);
        WormDotsIndicator dotsIndicator = view.findViewById(R.id.dotsIndicator);

        List<Integer> imagens = new ArrayList<>();
        imagens.add(R.drawable.ods_3);
        imagens.add(R.drawable.ods);
        imagens.add(R.drawable.slide_saudemental);
        imagens.add(R.drawable.slide_saudefisica);

        adapter = new CarrosselAdapter(imagens);
        viewPager2.setAdapter(adapter);

        dotsIndicator.setViewPager2(viewPager2);

        TextView textNome = view.findViewById(R.id.textNome);
        ImageView imagePerfil = view.findViewById(R.id.imagePerfil);


        String nomeUsuario = "Vitor"; // você pode carregar de SharedPreferences, banco, etc.
        textNome.setText("Olá, " + nomeUsuario);


        Glide.with(this)
                .load("https://exemplo.com/exemplo.jpg")
                .circleCrop()
                .placeholder(R.drawable.profile_icon)
                .into(imagePerfil);

        View.OnClickListener irParaPerfil = v -> {
            Fragment perfilFragment = new PerfilFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, perfilFragment) // esse ID deve ser o do FrameLayout da sua Activity que segura os fragments
                    .addToBackStack(null) // permite voltar com o botão "voltar"
                    .commit();

            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.telaperfil);

        };

        imagePerfil.setOnClickListener(irParaPerfil);
        textNome.setOnClickListener(irParaPerfil);

        searchHome.setOnClickListener(v -> {
            v.setEnabled(false); // evita toques duplos rápidos

            v.postDelayed(() -> {
                Fragment servicosFragment = new ServicosFragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, servicosFragment)
                        .addToBackStack(null)
                        .commit();

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.telaservicos);

                v.setEnabled(true);
            }, 150); // 150ms dá tempo do ripple animar
        });


        searchHome.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                String textoDigitado = searchHome.getText().toString();

                // Navegar para ServicosFragment
                ServicosFragment servicosFragment = new ServicosFragment();

                // Enviar o texto
                Bundle bundle = new Bundle();
                bundle.putString("textoBusca", textoDigitado);
                servicosFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, servicosFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.laranjaClaro, null));
        }
    }
}
