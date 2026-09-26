package br.com.davi.services;

import br.com.davi.model.Funcionario;

import java.util.List;

public class FuncionarioService {

    public void remover(String nome, List<Funcionario> funcionarios) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

}
