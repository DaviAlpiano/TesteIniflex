package br.com.davi;

import br.com.davi.model.Funcionario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.davi.services.FuncionarioService;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

public class Main {
    private static final List<Funcionario> funcionarios = carregarFuncionariosDoJson();
    private static final FuncionarioService funcionarioService = new FuncionarioService();
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATADOR_SALARIO = new DecimalFormat(
            "#,##0.00",
            new DecimalFormatSymbols(Locale.of("pt", "BR"))
    );

    public static void main(String[] args) {
        listarFuncionarios(funcionarios);

        System.out.println("---------Removendo João-----------");
        funcionarioService.remover("João", funcionarios);
        listarFuncionarios(funcionarios);

        System.out.println("---------Aumento Salarial-----------");

        funcionarioService.aumentoSalarialEmPorcentagem(funcionarios);
        listarFuncionarios(funcionarios);

        System.out.println("---------Agrupar por Função-----------");

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarioService.agruparFuncao(funcionarios);
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        System.out.println("---------Aniversário-----------");

        List<Funcionario> aniversariantes10e12 = funcionarioService.getAniversariantes(funcionarios, 10, 12);
        listarFuncionarios(aniversariantes10e12);

        System.out.println("---------Mais Velho-----------");

        Funcionario maisVelho = funcionarioService.getMaisVelho(funcionarios);
        funcionarioMaisVelho(maisVelho);
    }

    private static List<Funcionario> carregarFuncionariosDoJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("mock.json")) {
            if (is == null) {
                System.err.println("Arquivo mock.json não encontrado na pasta resources.");
                return new ArrayList<>();
            }
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            System.err.println("Erro ao processar o JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void listarFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.forEach(f -> System.out.printf(String.format(
                "Nome: %s | Data Nasc: %s | Salário: R$ %s | Função: %s%n",
                f.getNome(),
                f.getDataNascimento().format(FORMATADOR_DATA),
                FORMATADOR_SALARIO.format(f.getSalario()),
                f.getFuncao()
        )));
    }

    public static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n=== Função: " + funcao + " (" + lista.size() + ") ===");
            lista.forEach(f ->
                System.out.printf(String.format(
                        "  - %s | Nasc: %s | Salário: R$ %s%n",
                        f.getNome(),
                        f.getDataNascimento().format(FORMATADOR_DATA),
                        FORMATADOR_SALARIO.format(f.getSalario())
                ))
            );
        });
    }

    public static void funcionarioMaisVelho(Funcionario funcionario) {
        LocalDate dataNascimento = funcionario.getDataNascimento();
        LocalDate hoje = LocalDate.now();
        int idade = Period.between(dataNascimento, hoje).getYears();

        System.out.printf(String.format(
                "Nome: %s, Idade: %s anos%n",
                funcionario.getNome(),
                idade
        ));
    }

}