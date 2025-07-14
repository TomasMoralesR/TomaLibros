package com.challengealura.tomalibros.service;

public interface IConvierteDatos {
    <T> T obtainData(String json, Class <T> clase);
}
