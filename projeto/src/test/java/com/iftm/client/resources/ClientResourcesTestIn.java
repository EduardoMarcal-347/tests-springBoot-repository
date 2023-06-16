package com.iftm.client.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iftm.client.dto.ClientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourcesTestIn {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Verificar se o metodo insert retorna created(cod. 201) e o produto criado")
    public void testaInsert() throws Exception {
        ClientDTO clientDTO = new ClientDTO(45L,"Eduardo", "15497582698", 2000D, Instant.now(), 0);
        String json = objectMapper.writeValueAsString(clientDTO);

        ResultActions result =
                mockMvc.perform(post("/clients/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Eduardo"))
                .andExpect(jsonPath("$.cpf").value("15497582698"))
                .andExpect(jsonPath("$.id").value(45L));
    }

    @Test
    @DisplayName("Testa metodo delete(código 204) quando o id existir")
    public void testaDeleteIdNaoExistente() throws Exception {
        long idInexistente = 82L;

        ResultActions resultado = mockMvc.perform(delete("/clients/{id}",idInexistente)
                .accept(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Testa metodo delete (código 404) quando o id não existir")
    public void testaDeleteIdExistente() throws Exception {
        long idExistente = 2L;

        ResultActions resultado = mockMvc.perform(delete("/clients/{id}",idExistente)
                .accept(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Testa se metodo findByIncome retorna os clientes com salario acima de R$4500")
    public void testaFindByIncome() throws Exception {
        ResultActions resultado = mockMvc.perform(get("/clients/incomeGreaterThan/")
                .param("income", String.valueOf(4500.00))
                .accept(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 4L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 8L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists())
                .andExpect(jsonPath("$.numberOfElements").exists())
                .andExpect(jsonPath("$.numberOfElements").value(3))
                .andExpect(jsonPath("$.first").exists())
                .andExpect(jsonPath("$.first").value(true));
    }

    @Test
    @DisplayName("Testa metodo update para id existente")
    public void testaUpdateIdExistente() throws Exception {
        ClientDTO client = new ClientDTO(7L, "Maria", "12345678911", 2300.0, Instant.parse("2003-05-02T07:00:00Z"), 3);
        String json = objectMapper.writeValueAsString(client);

        ResultActions result = mockMvc.perform(put("/clients/{id}", client.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value(client.getCpf()))
                .andExpect(jsonPath("$.income").exists())
                .andExpect(jsonPath("$.income").value(client.getIncome()));
    }

    @Test
    @DisplayName("Testa metodo update para id inexistente")
    public void testaUpdateIdInexistente() throws Exception {
        ClientDTO client = new ClientDTO(55L, "Maria", "12345678911", 2300.0, Instant.parse("2003-05-02T07:00:00Z"), 3);
        String json = objectMapper.writeValueAsString(client);

        ResultActions result = mockMvc.perform(put("/clients/{id}", client.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"));
    }




}
