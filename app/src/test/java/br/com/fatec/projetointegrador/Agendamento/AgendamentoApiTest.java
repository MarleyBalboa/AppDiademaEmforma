package br.com.fatec.projetointegrador.Agendamento;

import br.com.fatec.projetointegrador.Retrofit.Api.AgendamentoApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.*;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.ProfissionalResumoComLocalDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.UsuarioResumoDTO;
import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class AgendamentoApiTest {

    private MockWebServer mockWebServer;
    private AgendamentoApi agendamentoApi;
    private final Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/")) // URL falsa
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        agendamentoApi = retrofit.create(AgendamentoApi.class);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testCriarAgendamentoComSucesso() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                "2025-06-15",
                "10:00",
                "Consulta de rotina",
                "CONSULTA",
                "AGUARDANDO_CONFIRMACAO",
                1L,
                2L,
                1L
        );

        AgendamentoResponseDTO mockResponse = new AgendamentoResponseDTO();
        mockResponse.setId(10L);
        mockResponse.setData("2025-06-15");
        mockResponse.setHora("10:00");
        mockResponse.setDescricao("Consulta de rotina");
        mockResponse.setTipo("CONSULTA");
        mockResponse.setStatus("AGUARDANDO_CONFIRMACAO");

        UsuarioResumoDTO usuarioResumo = new UsuarioResumoDTO();
        usuarioResumo.setId(1L);
        usuarioResumo.setUsuario("Carlos Santos");

        ProfissionalResumoComLocalDTO profResumo = new ProfissionalResumoComLocalDTO();
        profResumo.setProfissionalId(2L);
        profResumo.setUsuario("Jussara Bezerra");
        profResumo.setEspecialidade("FISIOTERAPEUTA");
        profResumo.setLocalNome("Academia X");

        mockResponse.setUsuarioCliente(usuarioResumo);
        mockResponse.setProfissionalResponsavel(profResumo);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(gson.toJson(mockResponse)));

        retrofit2.Response<AgendamentoResponseDTO> response =
                agendamentoApi.criarAgendamento(request).execute();

        assertTrue(response.isSuccessful());
        AgendamentoResponseDTO body = response.body();
        assertNotNull(body);
        assertEquals("Carlos Santos", body.getUsuarioCliente().getUsuario());
        assertEquals("Jussara Bezerra", body.getProfissionalResponsavel().getUsuario());
        assertEquals("Academia X", body.getProfissionalResponsavel().getLocalNome());
    }

    @Test
    public void testUsuarioClienteIncorreto() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                "2025-06-15",
                "10:00",
                "Treino leve",
                "TREINO",
                "AGUARDANDO_CONFIRMACAO",
                1L,
                2L,
                1L
        );

        AgendamentoResponseDTO mockResponse = new AgendamentoResponseDTO();
        mockResponse.setId(10L);
        mockResponse.setData("2025-06-15");
        mockResponse.setHora("10:00");
        mockResponse.setDescricao("Treino leve");
        mockResponse.setTipo("TREINO");
        mockResponse.setStatus("AGUARDANDO_CONFIRMACAO");

        UsuarioResumoDTO usuarioErrado = new UsuarioResumoDTO();
        usuarioErrado.setId(99L);
        usuarioErrado.setUsuario("Outro Usuário");

        mockResponse.setUsuarioCliente(usuarioErrado);
        mockResponse.setProfissionalResponsavel(new ProfissionalResumoComLocalDTO());

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(mockResponse)));

        retrofit2.Response<AgendamentoResponseDTO> response =
                agendamentoApi.criarAgendamento(request).execute();

        assertTrue(response.isSuccessful());
        assertNotEquals(1L, response.body().getUsuarioCliente().getId().longValue());
    }

    @Test
    public void testProfissionalResponsavelIncorreto() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                "2025-06-20",
                "08:00",
                "Consulta",
                "CONSULTA",
                "AGUARDANDO_CONFIRMACAO",
                3L,
                2L,
                1L
        );

        ProfissionalResumoComLocalDTO profErrado = new ProfissionalResumoComLocalDTO();
        profErrado.setProfissionalId(7L);
        profErrado.setUsuario("Profissional Errado");

        UsuarioResumoDTO usuario = new UsuarioResumoDTO();
        usuario.setId(3L);
        usuario.setUsuario("Carlos Santos");

        AgendamentoResponseDTO mockResponse = new AgendamentoResponseDTO();
        mockResponse.setId(77L);
        mockResponse.setData("2025-06-20");
        mockResponse.setHora("08:00");
        mockResponse.setDescricao("Consulta");
        mockResponse.setTipo("CONSULTA");
        mockResponse.setStatus("AGUARDANDO_CONFIRMACAO");
        mockResponse.setUsuarioCliente(usuario);
        mockResponse.setProfissionalResponsavel(profErrado);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(mockResponse)));

        retrofit2.Response<AgendamentoResponseDTO> response =
                agendamentoApi.criarAgendamento(request).execute();

        assertTrue(response.isSuccessful());
        assertNotEquals(2L, response.body().getProfissionalResponsavel().getProfissionalId().longValue());
    }

    @Test
    public void testTipoOuStatusInvalido() throws Exception {
        String requestJson =
                "{\n" +
                        "  \"data\": \"2025-06-22\",\n" +
                        "  \"hora\": \"14:00\",\n" +
                        "  \"descricao\": \"Sessão\",\n" +
                        "  \"tipo\": \"EXAME\",\n" + // inválido
                        "  \"status\": \"INVALIDO_STATUS\",\n" + // inválido
                        "  \"usuarioClienteId\": 5,\n" +
                        "  \"profissionalResponsavelId\": 4,\n" +
                        "  \"localId\": 1\n" +
                        "}";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400));

        AgendamentoRequestDTO request =
                gson.fromJson(requestJson, AgendamentoRequestDTO.class);

        retrofit2.Response<AgendamentoResponseDTO> response =
                agendamentoApi.criarAgendamento(request).execute();

        assertFalse(response.isSuccessful());
        assertEquals(400, response.code());
    }

}
