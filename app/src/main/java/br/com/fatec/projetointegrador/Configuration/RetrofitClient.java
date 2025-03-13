package br.com.fatec.projetointegrador.Configuration;

import br.com.fatec.projetointegrador.Backend.Api.UsuarioApi;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // Substituir pelo IP correto
    private static Retrofit retrofit = null;

    public static Retrofit getUsuarioApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

