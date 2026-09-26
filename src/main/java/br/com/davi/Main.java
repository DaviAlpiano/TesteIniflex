package br.com.davi;

import br.com.davi.model.Funcionario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = carregarFuncionariosDoJson();

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(f -> {
            System.out.println(String.format("Nome: %s | Data Nasc: %s | Salário: %,.2f | Função: %s",
                    f.getNome(),
                    f.getDataNascimento().format(formatadorData),
                    f.getSalario(),
                    f.getFuncao()
            ));
        });
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
}