package com.xyz.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.bookstore.config.PostgresTestContainer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @ClassRule
    public static PostgresTestContainer postgresTestContainer = PostgresTestContainer.getInstance();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

}
