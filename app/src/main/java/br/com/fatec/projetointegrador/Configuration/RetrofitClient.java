package br.com.fatec.projetointegrador.Configuration;

import com.google.gson.Gson;

import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;

    public RetrofitClient() {
        initializeRetrofit();
    }

    public void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080") // Altere para o IP correto
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

