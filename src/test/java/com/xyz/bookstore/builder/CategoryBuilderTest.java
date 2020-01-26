package com.xyz.bookstore.builder;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryBuilderTest {

    private CategoryBuilder subject;

    @Mock
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        subject = new CategoryBuilder(categoryService);
    }

    @Test
    public void test_fromName_should_call_category_service_and_fetch_category_by_name() {
        String categoryName = "category1";
        subject.fromName(categoryName);

        verify(categoryService).findByName(eq(categoryName));
    }

    @Test
    public void test_fromName_should_return_category_returned_from_service() {
        String categoryName = "category1";

        Category mockCategory = mock(Category.class);
        when(categoryService.findByName(categoryName)).thenReturn(mockCategory);
        Category result = subject.fromName(categoryName);

        assertThat(result, equalTo(mockCategory));

    }
}
