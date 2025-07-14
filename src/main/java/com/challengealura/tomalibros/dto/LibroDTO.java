package com.challengealura.tomalibros.dto;

public record LibroDTO (
        Long id,
        String title,
        String autor,
        String language,
        Double downloadCount
){}
