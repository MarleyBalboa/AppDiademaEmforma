package br.com.fatec.projetointegrador;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class TelaBuscaParques extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuração do layout antes de acessar componentes
        setContentView(R.layout.activity_tela_busca_parques);

        // Depois de chamar setContentView, acesse os componentes da UI
        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager2);

        // Criação da lista de locais
        List<TelaCardLocalizacao> telaCardLocalizacaos = new ArrayList<>();

        // Adicionando as localizações à lista
        TelaCardLocalizacao localizacaoParque1 = new TelaCardLocalizacao();
        localizacaoParque1.imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0c/64/f1/86/parque-do-paco-diadema.jpg?w=900&h=-1&s=1";
        localizacaoParque1.title = "Parque do Paço";
        localizacaoParque1.location = "Diadema";
        localizacaoParque1.starRating = 4.2f;
        telaCardLocalizacaos.add(localizacaoParque1);

        TelaCardLocalizacao localizacaoParque2 = new TelaCardLocalizacao();
        localizacaoParque2.imageUrl = "https://refugiosnointerior.com.br/sistema/_lib/file/img/lugar/975/eldodestaque.webp";
        localizacaoParque2.title = "Parque Ecológico do Eldorado";
        localizacaoParque2.location = "Diadema";
        localizacaoParque2.starRating = 3.9f;
        telaCardLocalizacaos.add(localizacaoParque2);

        TelaCardLocalizacao localizacaoParque3 = new TelaCardLocalizacao();
        localizacaoParque3.imageUrl = "https://d1lmhdch9ok5y9.cloudfront.net/lmCzIDfXoYZrSbfxQNDyPBPVnW0=/370x220/https%3A//d1q5r6jf4yxfe1.cloudfront.net/public/Imagem/2022/10/06/Parques.jpg";
        localizacaoParque3.title = "Parque Takebe";
        localizacaoParque3.location = "Diadema";
        localizacaoParque3.starRating = 3.0f;
        telaCardLocalizacaos.add(localizacaoParque3);

        // Configuração do Adapter
        locationsViewPager.setAdapter(new TelaCardLocalizacaoAdapter(telaCardLocalizacaos));

        // Habilitar navegação de borda a borda
        EdgeToEdge.enable(this);

        // Aplicar padding para os insets do sistema (barra de status, navegação)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
