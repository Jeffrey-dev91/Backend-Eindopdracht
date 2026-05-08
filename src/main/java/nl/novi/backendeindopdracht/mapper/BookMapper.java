package nl.novi.backendeindopdracht.mapper;
import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.models.Book;

public class BookMapper {

    private BookMapper() {}

    public static Book toEntity(BookInputDto bookInputDto) {
        Book book = new Book();
        book.setTitle(bookInputDto.title);
        book.setAuthor(bookInputDto.author);
        book.setIsbn(bookInputDto.isbn);
        book.setTotalCopies(bookInputDto.totalCopies);
        book.setAvailableCopies(bookInputDto.totalCopies);
        return book;
    }

    public static BookOutputDto toBookOutputDto(Book book) {
        BookOutputDto bookOutputDto = new BookOutputDto();
        bookOutputDto.setId(book.getId());
        bookOutputDto.setTitle(book.getTitle());
        bookOutputDto.setAuthor(book.getAuthor());
        bookOutputDto.setIsbn(book.getIsbn());
        bookOutputDto.setTotalCopies(book.getTotalCopies());
        bookOutputDto.setAvailableCopies(book.getAvailableCopies());

        if (book.getGenre() != null) {
            bookOutputDto.setGenreName(book.getGenre().getName());
        }

        if (book.getImagePath() != null) {
            bookOutputDto.setImageUrl("/books/" + book.getId() + "/image");
        }

        return bookOutputDto;
    }
}
