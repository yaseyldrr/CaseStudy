package UnitTest;

import utility.BookUtils;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class getBookDetailsAsStringTest {

    private BookUtils bookUtils;
    private BookUtils.BookRepository mockBookRepository;


    @BeforeTest
    void setUp() {
        mockBookRepository = Mockito.mock(BookUtils.BookRepository.class);
        bookUtils = new BookUtils(mockBookRepository); // dependency injection
    }

    @Test
    void testgetBookDetailsAsStringBook_NullTitle() {
        // Null title

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                null,
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_EmptyTitle() {
        // Empty title

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "",
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }
    @Test
    void testgetBookDetailsAsStringBook_Title_WithOnlyWhitespace() {
        // Space title

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                " ",
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_NullAuthors() {
        // Null author

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                null,
                2025
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_EmptyAuthors() {
        // Empty author

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of(""),
                2025
        ));

    }
    @Test
    void testgetBookDetailsAsStringBook_Author_WithOnlyWhitespace() {
        // Empty author

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of(" "),
                2025
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_ZeroYear() {
        // Zero year

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                0
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_NegativeYear() {
        // Negative year

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                -2025
        ));

    }

    @Test
    void testgetBookDetailsAsString_ValidBook_HasSubtitle_AnAuthor() {
        // Valid input with subtitle and an author
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                2025
        );
        assertEquals("Title: Subtitle by Author (2025)", result);

    }

    @Test
    void testgetBookDetailsAsString_ValidBook_HasSubtitle_TwoAuthors() {
        // Valid input with subtitle and many authors

        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);


        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author1", "Author2"),
                2025
        );

        assertEquals("Title: Subtitle by Author1, Author2 (2025)", result);

    }
    @Test
    void testgetBookDetailsAsString_ValidBook_HasSubtitle_ManyAuthors() {
        // Valid input with subtitle and many authors

        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);


        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author1", "Author2","Author3"),
                2025
        );

        assertEquals("Title: Subtitle by Author1, Author2, Author3 (2025)", result);

    }

    @Test
    void testgetBookDetailsAsString_ValidBook_EmptySubtitle_AnAuthor() {
        // Valid input with empty subtitle and an author
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "",
                List.of("Author"),
                2025
        );
        assertEquals("Title by Author (2025)", result);

    }
    @Test
    void testgetBookDetailsAsString_ValidBook_EmptySubtitle_TwoAuthors() {
        // Valid input with empty subtitle and two authors
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "",
                List.of("Author1","Author2"),
                2025
        );
        assertEquals("Title by Author1, Author2 (2025)", result);

    }

    @Test
    void testgetBookDetailsAsString_ValidBook_EmptySubtitle_ManyAuthors() {
        // Valid input with empty subtitle and many authors
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "",
                List.of("Author1", "Author2", "Author3"),
                2025
        );
        assertEquals("Title by Author1, Author2, Author3 (2025)", result);

    }

    @Test
    void testgetBookDetailsAsString_ValidBook_NullSubtitle_AnAuthor() {
        // Valid input with null subtitle and an author
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                null,
                List.of("Author"),
                2025
        );
        assertEquals("Title by Author (2025)", result);
    }
    @Test
    void testgetBookDetailsAsString_ValidBook_NullSubtitle_TwoAuthors() {
        // Valid input with null subtitle and two authors
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                null,
                List.of("Author1","Author2"),
                2025
        );
        assertEquals("Title by Author1, Author2 (2025)", result);
    }

    @Test
    void testgetBookDetailsAsString_ValidBook_NullSubtitle_ManyAuthors() {
        // Valid input with null subtitle and many authors
        when(mockBookRepository.isAvailable("Valid Book")).thenReturn(true);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                null,
                List.of("Author1", "Author2", "Author3"),
                2025
        );
        assertEquals("Title by Author1, Author2, Author3 (2025)", result);
    }

    @Test
    void testgetBookDetailsAsString_InvalidBook() {
        // Invalid input with invalid title

        when(mockBookRepository.isAvailable("Invalid Book")).thenReturn(false);

        String result = bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                2025
        );
        assertEquals("Book is unavailable!", result);
    }

}
