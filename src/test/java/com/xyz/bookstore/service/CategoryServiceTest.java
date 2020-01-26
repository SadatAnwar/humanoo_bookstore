package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.exception.CategoryNotFoundException;
import com.xyz.bookstore.repository.CategoryRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    private CategoryService subject;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        subject = new CategoryService(categoryRepository);
    }

    @Test
    public void test_findByName_should_lookup_in_repository() {
        String categoryName = "cat1";

        Category mockCategory = mock(Category.class);

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(mockCategory));
        subject.findByName(categoryName);

        verify(categoryRepository).findByName(categoryName);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void test_findByName_throw_entity_not_found_when_not_found() {
        String categoryName = "cat1";

        subject.findByName(categoryName);
    }
}
