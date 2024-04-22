package br.com.qrcred.convenio.model;

public interface SimpleCallback<T> {
    void onResponse (T response);
    void onError (String error);
}
