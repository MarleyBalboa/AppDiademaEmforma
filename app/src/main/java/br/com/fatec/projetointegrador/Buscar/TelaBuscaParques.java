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
import br.com.fatec.projetointegrador.GoogleMap.GMapParquesActivity;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.CadastroLogin.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Itens.TelaCardLocalizacao;

public class TelaBuscaParques extends AppCompatActivity {

    private RecyclerView.Adapter adapterDicasList;
    private RecyclerView recyclerViewDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout antes de acessar componentes
        setContentView(R.layout.activity_tela_busca_parques);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        initRecyclerView();

        LinearLayout layoutCardAgendar = findViewById(R.id.layoutCardParkAgendar);
        LinearLayout layoutCardLocal = findViewById(R.id.layoutCardParkLocal);
        LinearLayout layoutCardProf = findViewById(R.id.layoutCardParkProf);

        layoutCardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaParques.this, TelaAgendarActivity.class));
            }
        });
        layoutCardLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaParques.this, GMapParquesActivity.class));
            }
        });

        // Depois de chamar setContentView, acesse os componentes da UI
        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager2);

        // Criação da lista de locais
        List<TelaCardLocalizacao> telaCardLocalizacaos = new ArrayList<>();

        // Adicionando as localizações à lista
        TelaCardLocalizacao localizacaoParque1 = new TelaCardLocalizacao();
        localizacaoParque1.imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0c/64/f1/86/parque-do-paco-diadema.jpg?w=900&h=-1&s=1";
        localizacaoParque1.title = "Parque do Paço";
        localizacaoParque1.location = "Av. Antônio Piranga, 1380";
        localizacaoParque1.starRating = 4.3f;
        telaCardLocalizacaos.add(localizacaoParque1);

        TelaCardLocalizacao localizacaoParque2 = new TelaCardLocalizacao();
        localizacaoParque2.imageUrl = "https://refugiosnointerior.com.br/sistema/_lib/file/img/lugar/975/eldodestaque.webp";
        localizacaoParque2.title = "Parque do Eldorado";
        localizacaoParque2.location = "Av. Nossa Sra. dos Navegantes, 145";
        localizacaoParque2.starRating = 3.9f;
        telaCardLocalizacaos.add(localizacaoParque2);

        TelaCardLocalizacao localizacaoParque3 = new TelaCardLocalizacao();
        localizacaoParque3.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipPYAFVdX0dHExe1uepOcrKmOQH8wuRhMT5s3Fnt=s680-w680-h510";
        localizacaoParque3.title = "Parque Takebe";
        localizacaoParque3.location = "R. Yokohama, 118";
        localizacaoParque3.starRating = 4.3f;
        telaCardLocalizacaos.add(localizacaoParque3);

        TelaCardLocalizacao localizacaoParque4 = new TelaCardLocalizacao();
        localizacaoParque4.imageUrl = "https://lh3.googleusercontent.com/p/AF1QipO0KWVkZ9Eoo5uHMFy09FVvgnmN2_ecFCUROvsH=s680-w680-h510";
        localizacaoParque4.title = "Parque Regional Oeste";
        localizacaoParque4.location = "R. Érico Veríssimo, 311";
        localizacaoParque4.starRating = 4.5f;
        telaCardLocalizacaos.add(localizacaoParque4);

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

        items.add(new DicasESugestoes("Transforme o parque no seu centro de bem-estar","PDE","img_dicas_parques111"));
        items.add(new DicasESugestoes("A natureza e você, uma dupla imbatível!","PDE","img_dicas_parques22"));
        items.add(new DicasESugestoes("Respire fundo, mexa-se e liberte-se!","PDE","img_dicas_parques33"));
        items.add(new DicasESugestoes("Mude sua rotina, treine no parque!","PDE","img_dicas_parques44"));

        recyclerViewDicas = findViewById(R.id.recyclerViewParques);
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
