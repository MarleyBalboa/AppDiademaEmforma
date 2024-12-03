package br.com.fatec.projetointegrador.Itens;

public class DicasESugestoes {

    private String title;
    private String subtitle;
    private String picAdress;

    public DicasESugestoes(String title, String subtitle, String picAdress) {
        this.title = title;
        this.subtitle = subtitle;
        this.picAdress = picAdress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPicAdress() {
        return picAdress;
    }

    public void setPicAdress(String picAdress) {
        this.picAdress = picAdress;
    }
}
