package com.example.petropapi.data;

public interface StationRepositoryCallback<T> {
    void onSuccess(T data);

    void onError(Throwable error);
}
