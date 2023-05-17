package com.iftm.client.services;

import com.iftm.client.repositories.ClientRepository;
import com.iftm.client.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @InjectMocks
    ClientService service;

    @Mock
    ClientRepository repository;

    @Test
    @DisplayName("Testa metodo delete quando o id existir")
    public void testaDeleteIdExistente(){
        Long id = 1L;

        service.delete(id);

        Mockito.verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Testa metodo delete quando o id não existir")
    public void testaDeleteIdInexistente(){
        Long id = 129329323L;

        Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
    }


}
