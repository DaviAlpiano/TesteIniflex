package br.com.davi;

import br.com.davi.model.Funcionario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.davi.services.FuncionarioService;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Main {
    private static List<Funcionario> funcionarios = carregarFuncionariosDoJson();
    private static FuncionarioService funcionarioService = new FuncionarioService();

    public static void main(String[] args) {
        listarFuncionarios(funcionarios);

        System.out.println("---------Removendo João-----------");
        funcionarioService.remover("João", funcionarios);
        listarFuncionarios(funcionarios);
    }

    private static List<Funcionario> carregarFuncionariosDoJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("mock.json")) {
            if (is == null) {
                System.err.println("Arquivo mock.json não encontrado na pasta resources.");
                return new ArrayList<>();
            }
            return mapper.readValue(is, new TypeReference<List<Funcionario>>() {});
        } catch (Exception e) {
            System.err.println("Erro ao processar o JSON: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void listarFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatadorSalario = new DecimalFormat("#,##0.00", simbolos);

        funcionarios.forEach(f -> {
            System.out.println(String.format(
                    "Nome: %s | Data Nasc: %s | Salário: R$ %s | Função: %s",
                    f.getNome(),
                    f.getDataNascimento().format(formatadorData),
                    formatadorSalario.format(f.getSalario()),
                    f.getFuncao()
            ));
        });
    }

}