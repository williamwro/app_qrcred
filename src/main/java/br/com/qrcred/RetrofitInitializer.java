package br.com.qrcred;

import br.com.qrcred.services.CepService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitInitializer {
    private final Retrofit retrofit;
    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }
    public CepService getCep()
    {
        return retrofit.create(CepService.class);
    }
}
