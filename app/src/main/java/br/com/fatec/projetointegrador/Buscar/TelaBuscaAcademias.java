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
import br.com.fatec.projetointegrador.GoogleMap.GMapAcademiasActivity;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Itens.TelaCardLocalizacao;

public class TelaBuscaAcademias extends AppCompatActivity {

    private RecyclerView.Adapter adapterDicasList;
    private RecyclerView recyclerViewDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout antes de acessar componentes
        setContentView(R.layout.activity_tela_busca_academias);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        initRecyclerView();


        LinearLayout layoutCardAgendar = findViewById(R.id.layoutCardAcadAgendar);
        LinearLayout layoutCardLocal = findViewById(R.id.layoutCardAcadLocal);
        LinearLayout layoutCardProf = findViewById(R.id.layoutCardAcadProf);

        layoutCardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaAcademias.this, TelaAgendarActivity.class));
            }
        });
        layoutCardLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaBuscaAcademias.this, GMapAcademiasActivity.class));
            }
        });

        // Depois de chamar setContentView, acesse os componentes da UI
        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager2);

        // Criação da lista de locais
        List<TelaCardLocalizacao> telaCardLocalizacaos = new ArrayList<>();

        // Adicionando as localizações à lista
        TelaCardLocalizacao localizacaoAcademia1 = new TelaCardLocalizacao();
        localizacaoAcademia1.imageUrl = "https://static.wixstatic.com/media/a22bf1_8b3d6c83c93140f2879d758ebe1fcf38~mv2.jpg/v1/fill/w_602,h_600,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/241BC8F8-EFF7-49BD-A4F5-44F86BFFFD0B.jpg";
        localizacaoAcademia1.title = "Villa Fitness";
        localizacaoAcademia1.location = "R. Antônio Dias Adorno, 250";
        localizacaoAcademia1.starRating = 4.7f;
        telaCardLocalizacaos.add(localizacaoAcademia1);

        TelaCardLocalizacao localizacaoAcademia2 = new TelaCardLocalizacao();
        localizacaoAcademia2.imageUrl = "https://images2.nogueirense.com.br/wp-content/uploads/2019/02/img_1154-1550498617.jpg";
        localizacaoAcademia2.title = "Panobianco Diadema";
        localizacaoAcademia2.location = "Av. Pres. Kennedy, 488";
        localizacaoAcademia2.starRating = 4.1f;
        telaCardLocalizacaos.add(localizacaoAcademia2);

        TelaCardLocalizacao localizacaoAcademia3 = new TelaCardLocalizacao();
        localizacaoAcademia3.imageUrl = "https://pictures.smartfit.com.br/9321/big/1.jpg?1653331525";
        localizacaoAcademia3.title = "Smart Fit Diadema";
        localizacaoAcademia3.location = "Avenida Nossa Senhora das Vitórias, 188";
        localizacaoAcademia3.starRating = 4.4f;
        telaCardLocalizacaos.add(localizacaoAcademia3);

        TelaCardLocalizacao localizacaoAcademia4 = new TelaCardLocalizacao();
        localizacaoAcademia4.imageUrl = "https://cdn.prod.website-files.com/64dd05b33f019f79a7ec8f43/666300e6dec162f1e78c46d0_bluefit-1717764296305.jpeg";
        localizacaoAcademia4.title = "Bluefit Diadema";
        localizacaoAcademia4.location = "Av. Sete de Setembro, 97";
        localizacaoAcademia4.starRating = 4.7f;
        telaCardLocalizacaos.add(localizacaoAcademia4);

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

        items.add(new DicasESugestoes("Saiba como o exercício físico pode transformar sua saúde","PDE","img_dica_acad1"));
        items.add(new DicasESugestoes("Descubra o poder do exercício físico na sua saúde","PDE","copy_img_dica_acad33"));
        items.add(new DicasESugestoes("O segredo para uma vida mais longa e saudável","PDE","img_dica3_acad"));
        items.add(new DicasESugestoes("Transforme sua saúde com apenas 30 minutos por dia","PDE","img_dica_acad444"));

        recyclerViewDicas = findViewById(R.id.recyclerViewAcademias);
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