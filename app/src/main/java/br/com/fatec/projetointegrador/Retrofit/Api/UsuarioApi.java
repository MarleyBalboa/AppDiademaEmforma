package br.com.fatec.projetointegrador.Retrofit.Api;

import br.com.fatec.projetointegrador.Retrofit.Model.Auth.LoginRequest;
import br.com.fatec.projetointegrador.Retrofit.Model.Auth.LoginResponse;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.ProfissionalDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface UsuarioApi {

    @GET("/usuario/buscar-todos")
    Call<List<Usuario>> buscarTodosUsuarios();

    @GET("/usuario/buscar-todos-profissionais")
    Call<List<Usuario>> buscarTodosUsuariosProfissionais();

    @GET("/usuario/buscar-por-id/{id}")
    Call<Usuario> buscarUsuarioPorId(@Path("id") Long id);

    @GET("/usuario/buscar-por-email/{email}")
    Call<Usuario> buscarUsuarioPorEmail(@Path("email") String email);

    @POST("/auth/cadastro")
    Call<Usuario> criarUsuario(@Body Usuario cadastro);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest login);

    @PUT("/usuario/atualizar/{id}")
    Call<Usuario> atualizarUsuario(@Path("id") Long id, @Body Usuario usuario);

    @DELETE("/usuario/deletar/{id}")
    Call<Map<String, String>> deletarUsuario(@Path("id") Long id);
}
