package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

public class ProfissionalDTO {

    private Long id;
    private String usuario;
    private String papel;
    private String especialidade;

    public ProfissionalDTO(Long id, String usuario, String papel, String especialidade) {
        this.id = id;
        this.usuario = usuario;
        this.papel = papel;
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPapel() {
        return papel;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
