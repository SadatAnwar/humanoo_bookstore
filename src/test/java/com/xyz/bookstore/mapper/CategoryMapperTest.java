package com.xyz.bookstore.mapper;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.BookDto;
import com.xyz.bookstore.dto.CategoryDto;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryMapperTest {

    private CategoryMapper subject;

    @Mock
    private BookMapper bookMapper;

    @Before
    public void setUp() throws Exception {
        subject = new CategoryMapper(bookMapper);
    }

    @Test
    public void test_toDto_should_use_book_mapper_to_convert_book_to_bookDto() {
        Book mockBook = mock(Book.class);
        List<Book> listOfBooks = Arrays.asList(mockBook);

        Category mockCategory = mock(Category.class);

        when(mockCategory.getBooks()).thenReturn(listOfBooks);

        subject.toDto(mockCategory);

        verify(bookMapper).toDto(eq(listOfBooks));
    }

    @Test
    public void test_toDto_should_properly_write_category_to_dto() {
        Book mockBook = mock(Book.class);
        List<Book> listOfBooks = Arrays.asList(mockBook);

        Category mockCategory = mock(Category.class);
        when(mockCategory.getId()).thenReturn(1L);
        when(mockCategory.getName()).thenReturn("children");
        when(mockCategory.getBooks()).thenReturn(listOfBooks);

        BookDto mockBookDto = mock(BookDto.class);
        List<BookDto> bookDtos = Arrays.asList(mockBookDto);
        when(bookMapper.toDto(listOfBooks)).thenReturn(bookDtos);

        CategoryDto result = subject.toDto(mockCategory);

        assertThat(result.id, equalTo(1L));
        assertThat(result.name, equalTo("children"));
        assertThat(result.books, equalTo(bookDtos));
    }
}
