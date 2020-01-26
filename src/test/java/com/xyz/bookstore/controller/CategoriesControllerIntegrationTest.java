package com.xyz.bookstore.controller;

import com.xyz.bookstore.BaseIntegrationTest;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.CategoryDto;
import com.xyz.bookstore.repository.CategoryRepository;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoriesControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

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
    @Sql(statements = "insert into bookstore.categories (\"name\") VALUES ('blank category')")
    public void test_DELETE_category_with_id_will_delete_id_if_no_books_attached() throws Exception {

        Optional<Category> catToDelete = categoryRepository.findByName("blank category");

        assertThat(catToDelete.isPresent(), equalTo(true));
        Long id = catToDelete.get().getId();

        this.mockMvc
            .perform(
                delete("/api/categories/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());

        assertThat(categoryRepository.findById(id).isPresent(), equalTo(false));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void test_POST_category_will_create_new_category() throws Exception {

        CategoryDto dto = new CategoryDto();
        dto.name = "new category";

        this.mockMvc
            .perform(
                post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo("new category")));

        assertThat(categoryRepository.findByName(dto.name).isPresent(), equalTo(true));
    }

}
