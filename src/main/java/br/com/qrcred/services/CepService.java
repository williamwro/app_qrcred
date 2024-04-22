package br.com.qrcred.services;

import br.com.qrcred.viacep.ViaCEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {
    @GET("{id}/json")
    Call<ViaCEP> select(@Path("id") int id);
}
