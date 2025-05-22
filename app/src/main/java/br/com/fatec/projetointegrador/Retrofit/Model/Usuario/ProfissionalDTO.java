package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

public class ProfissionalDTO {

    private Long id;
    private String nome;
    private String papel;
    private String especialidade;

    public ProfissionalDTO(Long id, String nome, String papel, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.papel = papel;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPapel() {
        return papel;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
