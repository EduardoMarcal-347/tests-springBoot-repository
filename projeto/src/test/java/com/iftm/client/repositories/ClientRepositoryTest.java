package com.iftm.client.repositories;

import com.iftm.client.entities.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
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
    public void testarExcluirPorIdApagaRegistroExistente() {
        long idBuscado = 8;
        long quantidadeRegistrosEsperado = 11;

        repositorio.deleteById(idBuscado);
        Optional<Client> resultado = repositorio.findById(idBuscado);

        Assertions.assertThat(resultado).isEmpty();
        //assertTrue(resultado.isPresent());
        Assertions.assertThat(repositorio.count()).isEqualTo(quantidadeRegistrosEsperado);
        //assertEquals(quantidadeRegistrosEsperado, repositorio.count());

    }

    @Test
    @DisplayName("Verificar se método retorna o cliente com nome existente")
    public void testarBuscarPorNomeExistente() {
        String nomeBuscado = "Conceição Evaristo";
        long idEsperado = 1;

        Optional<Client> resultado = repositorio.findByName(nomeBuscado);

        Assertions.assertThat(resultado).isNotEmpty();
        Assertions.assertThat(resultado.get().getId()).isEqualTo(idEsperado);
    }

    @Test
    @DisplayName("Veificar se método retorna o cliente com nome não existente")
    public void testarBuscarPorNomeNãoExistente() {
        String nomeBuscado = "Eduardo Marçal";

        Optional<Client> resultado = repositorio.findByName(nomeBuscado);

        Assertions.assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Testar o método que retorna vários cliente com parte do nome similar ao texto\n" +
            "informado")
    public void testarBuscarPorNomeSimilar() {
        String textoBuscado = "ama";
        // Jose Saramago
        long primeiroIdEsperado = 7;
        // Chimamanda Adichie
        long segundoIdEsperado = 10;
        // Jorge Amado
        long terceiroIdEsperado = 12;

        List<Client> listaResultado = repositorio.findByNameContains(textoBuscado);

        Assertions.assertThat(listaResultado.size()).isEqualTo(3);
        Assertions.assertThat(listaResultado.get(0).getId()).isEqualTo(primeiroIdEsperado);
        Assertions.assertThat(listaResultado.get(1).getId()).isEqualTo(segundoIdEsperado);
        Assertions.assertThat(listaResultado.get(2).getId()).isEqualTo(terceiroIdEsperado);
    }

    @Test
    @DisplayName("Testar metodo que busca por nome similar passando um nome não existente")
    public void testarBuscarPorNomeSimilarNaoExistente() {
        String textoBuscado = "Marcal";

        List<Client> listaResultado = repositorio.findByNameContains(textoBuscado);

        Assertions.assertThat(listaResultado).isEmpty();
    }

    @Test
    @DisplayName("Testar o método que retorna vários cliente com parte do nome similar passando como\n" +
            "parametro um texto vazio (Neste caso teria que retornar todos os clientes)")
    public void testarBuscarPorNomeSimilarParametroVazio() {
        String textoVazio = "";

        List<Client> listaResultado = repositorio.findByNameContains(textoVazio);

        Assertions.assertThat(listaResultado.size()).isEqualTo(12);
    }

}
