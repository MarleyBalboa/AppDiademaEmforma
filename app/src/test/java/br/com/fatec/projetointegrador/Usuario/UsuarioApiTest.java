package br.com.fatec.projetointegrador.Usuario;

import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.*;
import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class UsuarioApiTest {

    private MockWebServer mockWebServer;
    private UsuarioApi usuarioApi;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/")) // URL falsa local
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioApi = retrofit.create(UsuarioApi.class);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testCriarUsuarioComum() throws Exception {
        Usuario usuarioEnviado = new Usuario("João da Silva", "joao@teste.com", "batatinha123", Papel.USUARIO_COMUM, null);
        usuarioEnviado.setId(1L);

        String jsonResponse = new Gson().toJson(usuarioEnviado);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(jsonResponse));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(usuarioEnviado).execute();

        assertTrue(response.isSuccessful());
        Usuario usuarioRecebido = response.body();
        assertNotNull(usuarioRecebido);
        assertEquals("João da Silva", usuarioRecebido.getUsuario());
        assertEquals("joao@teste.com", usuarioRecebido.getEmail());
        assertEquals(Papel.USUARIO_COMUM, usuarioRecebido.getPapel());
    }

    @Test
    public void testCriarUsuarioProfissional() throws Exception {
        Usuario usuarioEnviado = new Usuario("Pedro Pedroso", "pedro@teste.com", "pedro123", Papel.USUARIO_PROFISSIONAL, Especialidade.NUTRICIONISTA);
        usuarioEnviado.setId(1L);

        String jsonResponse = new Gson().toJson(usuarioEnviado);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(jsonResponse));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(usuarioEnviado).execute();

        assertTrue(response.isSuccessful());
        Usuario usuarioRecebido = response.body();
        assertNotNull(usuarioRecebido);
        assertEquals("Pedro Pedroso", usuarioRecebido.getUsuario());
        assertEquals("pedro@teste.com", usuarioRecebido.getEmail());
        assertEquals(Papel.USUARIO_PROFISSIONAL, usuarioRecebido.getPapel());
        assertEquals(Especialidade.NUTRICIONISTA, usuarioRecebido.getEspecialidade());
    }

    @Test
    public void testCriarUsuarioComNomeInvalido() throws Exception {
        String requestJson =
                "{\n" +
                        "  \"usuario\": 12345,\n" +  // nome como número (inválido)
                        "  \"email\": \"joao@teste.com\",\n" +
                        "  \"senha\": \"senha123\",\n" +
                        "  \"papel\": \"USUARIO_COMUM\",\n" +
                        "  \"especialidade\": null\n" +
                        "}";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(
                new Gson().fromJson(requestJson, Usuario.class)).execute();

        assertFalse(response.isSuccessful());
    }

    @Test
    public void testCriarUsuarioComEmailInvalido() throws Exception {
        String requestJson =
                "{\n" +
                        "  \"usuario\": \"João\",\n" +
                        "  \"email\": 123456,\n" + // email como número (inválido)
                        "  \"senha\": \"senha123\",\n" +
                        "  \"papel\": \"USUARIO_COMUM\",\n" +
                        "  \"especialidade\": null\n" +
                        "}";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(
                new Gson().fromJson(requestJson, Usuario.class)).execute();

        assertFalse(response.isSuccessful());
    }

    @Test
    public void testCriarUsuarioComPapelInvalido() throws Exception {
        String requestJson =
                "{\n" +
                        "  \"usuario\": \"João\",\n" +
                        "  \"email\": \"joao@teste.com\",\n" +
                        "  \"senha\": \"senha123\",\n" +
                        "  \"papel\": \"ADMIN\",\n" + // valor fora do enum
                        "  \"especialidade\": null\n" +
                        "}";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(
                new Gson().fromJson(requestJson, Usuario.class)).execute();

        assertFalse(response.isSuccessful());
    }

    @Test
    public void testCriarUsuarioComEspecialidadeInvalida() throws Exception {
        String requestJson =
                "{\n" +
                        "  \"usuario\": \"João\",\n" +
                        "  \"email\": \"joao@teste.com\",\n" +
                        "  \"senha\": \"senha123\",\n" +
                        "  \"papel\": \"USUARIO_PROFISSIONAL\",\n" +
                        "  \"especialidade\": \"MASSAGISTA\"\n" + // não está no enum
                        "}";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400));

        retrofit2.Response<Usuario> response = usuarioApi.criarUsuario(
                new Gson().fromJson(requestJson, Usuario.class)).execute();

        assertFalse(response.isSuccessful());
    }

}

