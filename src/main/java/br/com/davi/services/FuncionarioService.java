package br.com.davi.services;

import br.com.davi.model.Funcionario;

import java.math.BigDecimal;
import java.util.*;
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

    public List<Funcionario> getAniversariantes(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> mesesDesejados = Arrays.stream(meses)
                .boxed()
                .collect(Collectors.toSet());

        return funcionarios.stream()
                .filter(funcionario -> mesesDesejados.contains(funcionario.getDataNascimento().getMonthValue()))
                .toList();
    }

    public Funcionario getMaisVelho(List<Funcionario> funcionarios) {
        Optional<Funcionario> maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));

        return maisVelho.orElse(null);
    }

    public List<Funcionario> getFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome)).toList();
    }

}
