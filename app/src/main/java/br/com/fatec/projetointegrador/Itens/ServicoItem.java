package br.com.fatec.projetointegrador.Itens;

import java.util.List;

public class ServicoItem {
    private String title;
    private int imageResId;  // id do drawable
    private List<String> keywords;

    public ServicoItem(String title, int imageResId, List<String> keywords) {
        this.title = title;
        this.imageResId = imageResId;
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    // Você precisa desse método:
    public int getImageResId() {
        return imageResId;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}
