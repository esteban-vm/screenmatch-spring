package com.aluracursos.screenmatch.services;

public interface IDataConversor {
    <T> T getData(String json, Class<T> tClass);
}
