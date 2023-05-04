package com.iftm.client.repositories;

import com.iftm.client.entities.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository repositorio;

    @Test
    @DisplayName("Verificar se a busca por id realmente retorna o cliente correto.")
    public void testarBuscaPorIDRetornaClienteCorreto() {
        long idBuscado = 1; //corresponde ao primeiro registro do arquivo import.sql
        String nomeBuscado = "Conceição Evaristo";
        String cpfBuscado = "10619244881";

        Client respostaEsperada = new Client(1L, "Conceição Evaristo", "10619244881", null, null, null);

        Optional<Client> resposta = repositorio.findById(idBuscado);

        Assertions.assertThat(resposta.get().getName()).isEqualTo(nomeBuscado);
        Assertions.assertThat(resposta.get().getCpf()).isEqualTo(cpfBuscado);
    }

    @Test
    @DisplayName("Verificar se a busca por id inexistente retorna nenhum cliente")
    public void testarBuscaPorIdNaoRetornaObjetoParaIdInexistente() {
        long idBuscado = 100;

        Optional<Client> resultado = repositorio.findById(idBuscado);

        Assertions.assertThat(resultado).isEmpty();
        //assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Verificar se a exclusão realmente apaga um registro existente.")
    public void TestarExcluirPorIdApagaRegistroExistente() {
        long idBuscado = 8;
        long quantidadeRegistrosEsperado = 11;

        repositorio.deleteById(idBuscado);
        Optional<Client> resultado = repositorio.findById(idBuscado);

        Assertions.assertThat(resultado).isEmpty();
        //assertTrue(resultado.isPresent());
        Assertions.assertThat(repositorio.count()).isEqualTo(quantidadeRegistrosEsperado);
        //assertEquals(quantidadeRegistrosEsperado, repositorio.count());

    }

}
