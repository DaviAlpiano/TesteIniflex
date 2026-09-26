package br.com.davi.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Pessoa {
    private String nome;
    private LocalDate dataNascimento;
}
