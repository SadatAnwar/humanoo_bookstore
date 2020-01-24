package com.xyz.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.bookstore.dto.BookDto;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_GET_books_should_return_all_books() throws Exception
    {
        this.mockMvc
            .perform(get("/api/books")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void test_POST_book_will_insert_new_book_if_user_has_admin_role() throws Exception
    {
        BookDto mockBookDto = createMockBookDto();
        this.mockMvc
            .perform(
                post("/api/books")
                    .content(objectMapper.writeValueAsString(mockBookDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(mockBookDto.name)));
    }

    @Test
    public void test_POST_without_proper_role_will_return_UNAUTHORISED() throws Exception
    {
        BookDto mockBookDto = createMockBookDto();
        this.mockMvc
            .perform(
                post("/api/books")
                    .content(objectMapper.writeValueAsString(mockBookDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isUnauthorized());
    }

    private BookDto createMockBookDto()
    {
        BookDto dto = new BookDto();
        dto.name = "Hello World";
        dto.authorName = "Casper the friendly ghost";
        dto.isbn = "ISBN-1221312";
        dto.categories = Arrays.asList("Children");

        return dto;
    }
}
