package com.xyz.bookstore.controller;

import com.xyz.bookstore.BaseIntegrationTest;
import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.dto.BookDto;
import com.xyz.bookstore.repository.BooksRepository;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BooksControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void test_GET_books_should_return_all_books() throws Exception {
        this.mockMvc
            .perform(get("/api/books")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void test_GET_book_by_id_should_return_book() throws Exception {
        this.mockMvc
            .perform(get("/api/books/1")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void test_GET_book_by_id_respond_404_when_not_found() throws Exception {
        this.mockMvc
            .perform(get("/api/books/99")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void test_POST_book_will_insert_new_book_if_user_has_admin_role() throws Exception {
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
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = "INSERT INTO bookstore.books (isbn, name, author_name) " +
        "VALUES ('ISBN-121231231231', 'Some random book', 'random author')")
    public void test_DELETE_should_delete_book_from_store() throws Exception {
        Optional<Book> someBook = booksRepository.findAll().stream()
            .filter(b -> b.getName().equals("Some random book")).findAny();

        assertThat(someBook.isPresent(), equalTo(true));
        Book book = someBook.get();

        this.mockMvc
            .perform(
                delete("/api/books/" + book.getId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());

        assertThat(booksRepository.findById(book.getId()).isPresent(), equalTo(false));
    }

    @Test
    public void test_POST_without_proper_role_will_return_UNAUTHORISED() throws Exception {
        BookDto mockBookDto = createMockBookDto();
        this.mockMvc
            .perform(
                post("/api/books")
                    .content(objectMapper.writeValueAsString(mockBookDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isUnauthorized());
    }

    private BookDto createMockBookDto() {
        BookDto dto = new BookDto();
        dto.name = "Hello World";
        dto.authorName = "Casper the friendly ghost";
        dto.isbn = "ISBN-1221312";
        dto.categories = Arrays.asList("Children");

        return dto;
    }
}
