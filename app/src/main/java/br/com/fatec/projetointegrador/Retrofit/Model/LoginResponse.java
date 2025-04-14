package br.com.fatec.projetointegrador.Retrofit.Model;

public class LoginResponse {
    private Long id;
    private String nome;
    private String email;
    private String token;

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}