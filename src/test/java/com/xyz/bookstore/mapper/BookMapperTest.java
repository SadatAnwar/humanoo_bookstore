package com.xyz.bookstore.mapper;

import com.xyz.bookstore.builder.CategoryBuilder;
import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.BookDto;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookMapperTest {

    private BookMapper subject;

    @Mock
    private CategoryBuilder categoryBuilder;

    @Before
    public void setUp() throws Exception {
        subject = new BookMapper(categoryBuilder);
    }

    @Test
    public void test_toDto_should_convert_entity_to_dto() {
        Book mockBook = mockBook();

        BookDto result = subject.toDto(mockBook);

        assertThat(result.id, equalTo(mockBook.getId()));
        assertThat(result.name, equalTo(mockBook.getName()));
        assertThat(result.authorName, equalTo(mockBook.getAuthorName()));
        assertThat(result.isbn, equalTo(mockBook.getIsbn()));
        assertThat(result.categories, equalTo(mockBook.getCategories().stream().map(Category::getName).collect(Collectors.toList())));
        assertThat(result.createdAt, equalTo(mockBook.getCreatedAt()));
        assertThat(result.updatedAt, equalTo(mockBook.getUpdatedAt()));
    }

    @Test
    public void test_toDto_should_convert_list_of_books_to_list_of_dtos() {
        Book mockBook1 = mockBook();
        Book mockBook2 = mockBook();
        when(mockBook2.getId()).thenReturn(2L);

        List<BookDto> results = subject.toDto(Arrays.asList(mockBook1, mockBook2));

        assertThat(results.size(), equalTo(2));
        assertThat(results.get(0).id, equalTo(1L));
        assertThat(results.get(1).id, equalTo(2L));

    }

    @Test
    public void test_toEntity_should_call_category_builder_to_build_category_from_string_name() {
        BookDto dto = new BookDto();
        dto.categories = Arrays.asList("category1", "category2");

        subject.toEntity(dto);

        verify(categoryBuilder).fromName("category1");
        verify(categoryBuilder).fromName("category2");
    }

    @Test
    public void test_toEntity_converts_dto_to_entity() {
        BookDto dto = new BookDto();
        dto.name = "Spring";
        dto.isbn = "123";
        dto.authorName = "Abc";
        dto.categories = Arrays.asList("category1");

        Category mockCategory = mock(Category.class);

        when(categoryBuilder.fromName("category1")).thenReturn(mockCategory);

        Book result = subject.toEntity(dto);

        assertThat(result.getName(), equalTo(dto.name));
        assertThat(result.getIsbn(), equalTo(dto.isbn));
        assertThat(result.getAuthorName(), equalTo(dto.authorName));
        assertThat(result.getCategories(), equalTo(Arrays.asList(mockCategory)));

    }


    private Book mockBook() {
        Long bookId = 1L;
        String bookName = "hello world";
        String authorName = "Mary Poppins";
        String isbn = "123456";
        String categoryName = "children";

        LocalDateTime bookUpdatedAt = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        LocalDateTime bookCreatedAt = LocalDateTime.now().minus(1, ChronoUnit.DAYS);

        Category mockCategory = mock(Category.class);
        when(mockCategory.getName()).thenReturn(categoryName);

        Book book = mock(Book.class);
        when(book.getId()).thenReturn(bookId);
        when(book.getName()).thenReturn(bookName);
        when(book.getAuthorName()).thenReturn(authorName);
        when(book.getIsbn()).thenReturn(isbn);
        when(book.getCreatedAt()).thenReturn(bookCreatedAt);
        when(book.getUpdatedAt()).thenReturn(bookUpdatedAt);
        when(book.getCategories()).thenReturn(Arrays.asList(mockCategory));

        return book;
    }
}
