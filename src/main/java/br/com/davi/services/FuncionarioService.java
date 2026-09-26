package br.com.davi.services;

import br.com.davi.model.Funcionario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuncionarioService {

    public void remover(String nome, List<Funcionario> funcionarios) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    public void aumentoSalarialEmPorcentagem(List<Funcionario> funcionarios) {
        BigDecimal bonus = BigDecimal.valueOf(1.1);
        funcionarios.forEach(funcionario ->
                funcionario.setSalario(
                        funcionario.getSalario().multiply(bonus))
        );
    }

    public Map<String, List<Funcionario>> agruparFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

}
