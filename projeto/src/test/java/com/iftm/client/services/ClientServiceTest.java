package com.iftm.client.services;

import org.aspectj.apache.bcel.Repository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @InjectMocks
    ClientService service;

    @Mock
    Repository repository;

    


}
