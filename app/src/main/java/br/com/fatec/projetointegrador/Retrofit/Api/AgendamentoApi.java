package br.com.fatec.projetointegrador.Retrofit.Api;

import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.Agendamento;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoRequestDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AgendamentoApi {

    @GET("/agendamento/buscar/{id}")
    Call<Agendamento> buscarAgendamentoPorId(@Path("id") Long id);

    @POST("/agendamento/criar")
    Call<AgendamentoResponseDTO> criarAgendamento(@Body AgendamentoRequestDTO agendamento);
}
