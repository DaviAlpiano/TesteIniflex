package br.com.davi.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa {
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
}
