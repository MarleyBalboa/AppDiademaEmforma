package br.com.fatec.projetointegrador.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Adapter.ServicosAdapter;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaAcademias;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaNutricionista;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaParques;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaSaudeMental;
import br.com.fatec.projetointegrador.CadastroLogin.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Itens.ServicoItem;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Fragments.HomeFragment;

public class ServicosFragment extends Fragment {

    private EditText etSearch;
    private RecyclerView recyclerView;
    private ServicosAdapter adapter;
    private List<ServicoItem> servicoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servicos, container, false);

        ImageView btnVoltar = view.findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        });


        etSearch = view.findViewById(R.id.et_search);
        recyclerView = view.findViewById(R.id.recycler_servicos);

        servicoList = new ArrayList<>();
        servicoList.add(new ServicoItem("SAÚDE MENTAL", R.drawable.icone_saude_mental,
                List.of("mental", "psicólogo", "terapia", "emoções")));
        servicoList.add(new ServicoItem("SAÚDE FÍSICA", R.drawable.icone_saude_fisica,
                List.of("academia", "exercício", "atividade", "fisica")));
        servicoList.add(new ServicoItem("PARQUES", R.drawable.icone_parque,
                List.of("parques", "natureza", "lazer", "caminhada")));
        servicoList.add(new ServicoItem("SAÚDE ALIMENTAR", R.drawable.icone_nutrucao,
                List.of("nutrição", "alimentar", "comida", "nutricionista", "saudável")));
        servicoList.add(new ServicoItem("AGENDAR", R.drawable.icone_agendar,
                List.of("agendar", "consulta", "horário", "agendamento")));

        adapter = new ServicosAdapter(requireContext(), servicoList, item -> {
            switch (item.getTitle()) {
                case "AGENDAR":
                    startActivity(new Intent(requireContext(), TelaAgendarActivity.class));
                    break;
                case "SAÚDE FÍSICA":
                    startActivity(new Intent(requireContext(), TelaBuscaAcademias.class));
                    break;
                case "PARQUES":
                    startActivity(new Intent(requireContext(), TelaBuscaParques.class));
                    break;
                case "SAÚDE ALIMENTAR":
                    startActivity(new Intent(requireContext(), TelaBuscaNutricionista.class));
                    break;
                case "SAÚDE MENTAL":
                    startActivity(new Intent(requireContext(), TelaBuscaSaudeMental.class));
                    break;
                default:
                    Toast.makeText(requireContext(), "Serviço não disponível", Toast.LENGTH_SHORT).show();
            }
        });

        TextView textNome = view.findViewById(R.id.textNome);
        ImageView imagePerfil = view.findViewById(R.id.imagePerfil);

        String nomeUsuario = "Usuário"; // você pode carregar de SharedPreferences, banco, etc.
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
                    .replace(R.id.frame_layout, perfilFragment)
                    .addToBackStack(null)
                    .commit();

            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.telaperfil);
        };

        imagePerfil.setOnClickListener(irParaPerfil);
        textNome.setOnClickListener(irParaPerfil);

        View.OnClickListener irParaHome = v -> {
            Fragment homefragment = new HomeFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, homefragment) // esse ID deve ser o do FrameLayout da sua Activity que segura os fragments
                    .addToBackStack(null) // permite voltar com o botão "voltar"
                    .commit();

            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.telainicio);

        };

        btnVoltar.setOnClickListener(irParaHome);


        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);


        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.filter(s.toString());
                    recyclerView.post(() -> {
                        recyclerView.invalidate();
                        recyclerView.requestLayout();
                    });

                    if (adapter.getItemCount() == 0) {
                        Toast.makeText(getContext(), "Nenhum serviço encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Bundle args = getArguments();
            if (args != null && args.containsKey("textoBusca")) {
                String texto = args.getString("textoBusca", "");
                etSearch.setText(texto);
                etSearch.requestFocus();

                etSearch.post(() -> {
                    InputMethodManager imm = (InputMethodManager) requireActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.laranjaClaro, null));
        }

        return view;
    }
}
