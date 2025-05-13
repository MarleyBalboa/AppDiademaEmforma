package br.com.fatec.projetointegrador.Retrofit.Api;

import java.util.List;

import br.com.fatec.projetointegrador.Retrofit.Model.Local.LocalDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LocalApi {

    @GET("/local/buscar")
    Call<List<LocalDTO>> buscarTodosLocais();

}
