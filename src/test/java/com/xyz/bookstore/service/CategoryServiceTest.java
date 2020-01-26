package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.exception.CategoryNotEmptyException;
import com.xyz.bookstore.exception.CategoryNotFoundException;
import com.xyz.bookstore.repository.CategoryRepository;
import java.util.Arrays;
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
    public void test_loadByName_should_lookup_in_repository() {
        String categoryName = "cat1";

        Category mockCategory = mock(Category.class);

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(mockCategory));
        subject.loadByName(categoryName);

        verify(categoryRepository).findByName(categoryName);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void test_loadByName_throw_entity_not_found_when_not_found() {
        String categoryName = "cat1";

        subject.loadByName(categoryName);
    }

    @Test
    public void test_save_category_should_call_save_on_repository() {
        Category mockCategory = mock(Category.class);

        subject.saveCategory(mockCategory);

        verify(categoryRepository).save(mockCategory);
    }

    @Test(expected = CategoryNotEmptyException.class)
    public void test_safeDeleteCategory_should_throw_exception_if_books_are_attached_to_category() {
        Book mockBook = mock(Book.class);
        Category mockCategory = mock(Category.class);

        when(mockCategory.getBooks()).thenReturn(Arrays.asList(mockBook));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        subject.safeDeleteCategory(1L);
    }
}
