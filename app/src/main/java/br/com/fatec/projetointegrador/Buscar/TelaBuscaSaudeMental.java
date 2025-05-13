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
import br.com.fatec.projetointegrador.GoogleMap.GMapSaudeMentalActivity;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Itens.TelaCardLocalizacao;

public class TelaBuscaSaudeMental extends AppCompatActivity {

    private RecyclerView.Adapter adapterDicasList;
    private RecyclerView recyclerViewDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout antes de acessar componentes
        setContentView(R.layout.activity_tela_busca_saude_mental);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        initRecyclerView();

        LinearLayout layoutCardAgendar = findViewById(R.id.layoutCardPsiAgendar);
        LinearLayout layoutCardLocal = findViewById(R.id.layoutCardPsiLocal);
        LinearLayout layoutCardProf = findViewById(R.id.layoutCardPsiProf);

        layoutCardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaSaudeMental.this, TelaAgendarActivity.class));
            }
        });
        layoutCardLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaSaudeMental.this, GMapSaudeMentalActivity.class));
            }
        });

        // Depois de chamar setContentView, acesse os componentes da UI
        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager2);

        // Criação da lista de locais
        List<TelaCardLocalizacao> telaCardLocalizacaos = new ArrayList<>();

        // Adicionando as localizações à lista
        TelaCardLocalizacao localizacaoSaudeMental1 = new TelaCardLocalizacao();
        localizacaoSaudeMental1.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipMqEgDpePsuh6DBhKG31NcER03J3OCIafQDVdQY=s680-w680-h510";
        localizacaoSaudeMental1.title = "CEPAP - Psicólogos | Psiquiatra Diadema";
        localizacaoSaudeMental1.location = "R. Orense, 41 - Sala 414";
        localizacaoSaudeMental1.starRating = 4.8f;
        telaCardLocalizacaos.add(localizacaoSaudeMental1);

        TelaCardLocalizacao localizacaoSaudeMental2 = new TelaCardLocalizacao();
        localizacaoSaudeMental2.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipPCghpv0ZeAtWngcuJXgdFXc9AFai5xqZC2WB4h=s680-w680-h510";
        localizacaoSaudeMental2.title = "OPUS Clínica de Psicologia e Neuropsicologia";
        localizacaoSaudeMental2.location = "Rua das Turmalinas, 132 - Sala 9";
        localizacaoSaudeMental2.starRating = 5.0f;
        telaCardLocalizacaos.add(localizacaoSaudeMental2);

        TelaCardLocalizacao localizacaoSaudeMental3 = new TelaCardLocalizacao();
        localizacaoSaudeMental3.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipMQ1X5V3_DHu9w6H2hO5-IKgSanMpyCIlO9wQ8G=s680-w680-h510";
        localizacaoSaudeMental3.title = "Psicóloga Elizete Ferreira";
        localizacaoSaudeMental3.location = "Av. Nossa Sra. das Vitórias, 248";
        localizacaoSaudeMental3.starRating = 4.9f;
        telaCardLocalizacaos.add(localizacaoSaudeMental3);

        TelaCardLocalizacao localizacaoSaudeMental4 = new TelaCardLocalizacao();
        localizacaoSaudeMental4.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipNXTTnUdA5J9bIiJFLaSLTVHVkVpSs5wCw-Py70=s680-w680-h510";
        localizacaoSaudeMental4.title = "Consultório de Psicologia Ativaamente";
        localizacaoSaudeMental4.location = "R. Manoel da Nóbrega, 628 - Sala 88";
        localizacaoSaudeMental4.starRating = 4.7f;
        telaCardLocalizacaos.add(localizacaoSaudeMental4);

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

        items.add(new DicasESugestoes("Cuide da sua mente, ela cuida de você","PDE","img_dicas_sm"));
        items.add(new DicasESugestoes("Saúde mental: A base de uma vida plena","PDE","img_dicas_sm22"));
        items.add(new DicasESugestoes("7 Dicas para cultivar a saúde mental","PDE","img_dicas_sm33"));
        items.add(new DicasESugestoes("Sua saúde mental é a chave para uma vida equilibrada","PDE","img_dicas_sm444"));

        recyclerViewDicas = findViewById(R.id.recyclerViewSaudeMental);
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
