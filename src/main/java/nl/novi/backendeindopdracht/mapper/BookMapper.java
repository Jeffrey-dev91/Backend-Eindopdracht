package nl.novi.backendeindopdracht.mapper;


import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.models.Book;

public class BookMapper {


private BookMapper() {

}



public static Book toEntity(BookInputDto bookInputDto) {

    Book book = new Book();

    book.setTitle(bookInputDto.title);
    book.setAuthor(bookInputDto.author);
    book.setIsbn(bookInputDto.isbn);
    book.setTotalCopies(bookInputDto.totalCopies);
    return book;

}


public static BookOutputDto toBookOutputDto(Book book) {

    BookOutputDto bookOutputDto = new BookOutputDto();

    bookOutputDto.id = book.getId();
    bookOutputDto.title = book.getTitle();
    bookOutputDto.author = book.getAuthor();
    bookOutputDto.isbn = book.getIsbn();
    bookOutputDto.totalCopies = book.getTotalCopies();
    bookOutputDto.availableCopies = book.getAvailableCopies();


    if (book.getGenre() != null) {
        bookOutputDto.genreName = book.getGenre().getName();
    }

    return bookOutputDto;

}


}
