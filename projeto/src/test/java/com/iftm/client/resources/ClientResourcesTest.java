package com.iftm.client.resources;

import com.iftm.client.services.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Test
    @DisplayName("Verificar se o metodo insert retorna created(cod. 201) e o produto criado")
    public void testaInsert(){
        
    }

}
