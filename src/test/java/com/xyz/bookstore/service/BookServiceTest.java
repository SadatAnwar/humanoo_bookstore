package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.exception.BookNotFoundException;
import com.xyz.bookstore.repository.BooksRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private BookService subject;

    @Mock
    private BooksRepository booksRepository;

    @Before
    public void setUp() throws Exception {
        subject = new BookService(booksRepository);
    }

    @Test
    public void test_getAllBooks_should_call_find_all_in_repo() {

        Book mockBook = mock(Book.class);
        when(booksRepository.findAll()).thenReturn(Arrays.asList(mockBook));

        List<Book> result = subject.getAllBooks();

        verify(booksRepository).findAll();

        assertThat(result, hasSize(1));
        assertThat(result.get(0), equalTo(mockBook));

    }

    @Test
    public void test_loadBookById_should_return_book_from_repo_when_present() {
        Long bookId = 1L;

        Book mockBook = mock(Book.class);
        when(booksRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        Book result = subject.loadBookById(bookId);

        assertThat(result, equalTo(mockBook));
    }

    @Test(expected = BookNotFoundException.class)
    public void test_loadBookById_should_throw_not_found_exception_when_no_book_found() {
        subject.loadBookById(1L);
    }

    @Test
    public void test_addNewBook_should_call_save_on_repo() {
        Book mockBook = mock(Book.class);

        subject.addNewBook(mockBook);

        verify(booksRepository).save(eq(mockBook));
    }

    @Test
    public void test_deleteById_should_call_delete_on_repository() {
        Book mockBook = mock(Book.class);
        when(booksRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        subject.deleteBookById(1L);

        verify(booksRepository).delete(mockBook);

    }
}
