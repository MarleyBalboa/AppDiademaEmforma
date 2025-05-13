package br.com.fatec.projetointegrador.Buscar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Adapter.DicasESugestoesAdapter;
import br.com.fatec.projetointegrador.Adapter.TelaCardLocalizacaoAdapter;
import br.com.fatec.projetointegrador.Itens.DicasESugestoes;
import br.com.fatec.projetointegrador.GoogleMap.GMapNutricaoActivity;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Itens.TelaCardLocalizacao;

public class TelaBuscaNutricionista extends AppCompatActivity {

    private RecyclerView.Adapter adapterDicasList;
    private RecyclerView recyclerViewDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout antes de acessar componentes
        setContentView(R.layout.activity_tela_busca_nutricionista);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        initRecyclerView();


        LinearLayout layoutCardAgendar = findViewById(R.id.layoutCardNutriAgendar);
        LinearLayout layoutCardLocal = findViewById(R.id.layoutCardNutriLocal);
        LinearLayout layoutCardProf = findViewById(R.id.layoutCardNutriProf);

        layoutCardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaNutricionista.this, TelaAgendarActivity.class));
            }
        });
        layoutCardLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaNutricionista.this, GMapNutricaoActivity.class));
            }
        });

        // Depois de chamar setContentView, acesse os componentes da UI
        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager2);

        // Criação da lista de locais
        List<TelaCardLocalizacao> telaCardLocalizacaos = new ArrayList<>();

        // Adicionando as localizações à lista
        TelaCardLocalizacao localizacaoNutricionista1 = new TelaCardLocalizacao();
        localizacaoNutricionista1.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipPZx3hecO2Tm6EeRVXoIIP8Kd5kWKWEzf22hvCi=s680-w680-h510";
        localizacaoNutricionista1.title = "Nutricionista Debora Santos";
        localizacaoNutricionista1.location = "R. Sebastião Ferreira Leite, 219";
        localizacaoNutricionista1.starRating = 4.7f;
        telaCardLocalizacaos.add(localizacaoNutricionista1);

        TelaCardLocalizacao localizacaoNutricionista2 = new TelaCardLocalizacao();
        localizacaoNutricionista2.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipMvB6F17j0D88SRcaIPo-7cbNUxBtXkTrxD6luw=s680-w680-h510";
        localizacaoNutricionista2.title = "Fitness Vip Studio R & A";
        localizacaoNutricionista2.location = "R. Ari Barroso, 249";
        localizacaoNutricionista2.starRating = 5.0f;
        telaCardLocalizacaos.add(localizacaoNutricionista2);

        TelaCardLocalizacao localizacaoNutricionista3 = new TelaCardLocalizacao();
        localizacaoNutricionista3.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipM3Jqo4pzHKpvOlKifmRL0bX49OonJTwma5qIc=s680-w680-h510";
        localizacaoNutricionista3.title = "Nutri Paloma Santos";
        localizacaoNutricionista3.location = "R. Orense, 41";
        localizacaoNutricionista3.starRating = 4.8f;
        telaCardLocalizacaos.add(localizacaoNutricionista3);

        TelaCardLocalizacao localizacaoNutricionista4 = new TelaCardLocalizacao();
        localizacaoNutricionista4.imageUrl = "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=bedbODvaCKWhC-1Ded6EPQ&cb_client=search.gws-prod.gps&w=408&h=240&yaw=72.327034&pitch=0&thumbfov=100";
        localizacaoNutricionista4.title = "Nutricionista Thainá Passos";
        localizacaoNutricionista4.location = "Rua Manoel da Nóbrega, 735";
        localizacaoNutricionista4.starRating = 4.5f;
        telaCardLocalizacaos.add(localizacaoNutricionista4);

        // Configuração do Adapter
        locationsViewPager.setAdapter(new TelaCardLocalizacaoAdapter(telaCardLocalizacaos));

        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        locationsViewPager.setPageTransformer(compositePageTransformer);

        // Habilitar navegação de borda a borda
        EdgeToEdge.enable(this);

        // Aplicar padding para os insets do sistema (barra de status, navegação)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initRecyclerView() {
        ArrayList<DicasESugestoes> items = new ArrayList<>();

        items.add(new DicasESugestoes("Alimentação saudável, vida saudável","PDE","img_dicas_nutri11"));
        items.add(new DicasESugestoes("6 estratégias simples para uma alimentação saudável","PDE","img_dicas_nutri222"));
        items.add(new DicasESugestoes("Cuide do seu corpo com a alimentação certa","PDE","img_dicas_nutri33"));
        items.add(new DicasESugestoes("Veja algumas dicas para manter uma dieta balanceada","PDE","img_dicas_nutri444"));

        recyclerViewDicas = findViewById(R.id.recyclerViewNutricao);
        recyclerViewDicas.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapterDicasList = new DicasESugestoesAdapter(items);
        recyclerViewDicas.setAdapter(adapterDicasList);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Voltar para a tela anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}