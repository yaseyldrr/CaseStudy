package UnitTest;

import utility.BookUtils;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class TestGetBookDetailsAsString {

    private BookUtils bookUtils;
    private BookUtils.BookRepository mockBookRepository;


    @BeforeTest
    void setUp() {
        mockBookRepository = Mockito.mock(BookUtils.BookRepository.class);
        bookUtils = new BookUtils(mockBookRepository);
    }

    @Test
    void testGetBookDetailsAsStringBook_NullTitle() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                null,
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }

    @Test
    void testGetBookDetailsAsStringBook_EmptyTitle() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "",
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }
    @Test
    void testGetBookDetailsAsStringBook_Title_WithOnlyWhiteSpace() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                " ",
                "Subtitle",
                List.of("Author"),
                2025
        ));

    }

    @Test
    void testGetBookDetailsAsStringBook_NullAuthors() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                null,
                2025
        ));

    }

    @Test
    void testGetBookDetailsAsStringBook_EmptyAuthors() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of(""),
                2025
        ));

    }
    @Test
    void testGetBookDetailsAsStringBook_Author_WithOnlyWhiteSpace() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of(" "),
                2025
        ));

    }

    @Test
    void testgetBookDetailsAsStringBook_ZeroYear() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                0
        ));

    }

    @Test
    void testGetBookDetailsAsStringBook_NegativeYear() {

        assertThrows(IllegalArgumentException.class, () -> bookUtils.getBookDetailsAsString(
                "Title",
                "Subtitle",
                List.of("Author"),
                -2025
        ));

    }

    @Test
    void testGetBookDetailsAsString_ValidBook_HasSubtitle_AnAuthor() {
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
    void testGetBookDetailsAsString_ValidBook_HasSubtitle_TwoAuthors() {

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
    void testGetBookDetailsAsString_ValidBook_HasSubtitle_ManyAuthors() {

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
    void testGetBookDetailsAsString_ValidBook_EmptySubtitle_AnAuthor() {
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
    void testGetBookDetailsAsString_ValidBook_EmptySubtitle_TwoAuthors() {
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
    void testGetBookDetailsAsString_ValidBook_EmptySubtitle_ManyAuthors() {
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
    void testGetBookDetailsAsString_ValidBook_NullSubtitle_AnAuthor() {
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
    void testGetBookDetailsAsString_ValidBook_NullSubtitle_TwoAuthors() {

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
    void testGetBookDetailsAsString_ValidBook_NullSubtitle_ManyAuthors() {

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
    void testGetBookDetailsAsString_InvalidBook() {

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
