package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.LoanInputDto;
import nl.novi.backendeindopdracht.dto.LoanOutputDto;
import nl.novi.backendeindopdracht.mapper.LoanMapper;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.models.Loan;
import nl.novi.backendeindopdracht.models.User;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.LoanRepository;
import nl.novi.backendeindopdracht.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
private final UserRepository userRepository;





public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {

    this.loanRepository = loanRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;

}


public LoanOutputDto createLoan(LoanInputDto loanInputDto) {



    Book book = bookRepository.findById(loanInputDto.bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));


    User user = userRepository.findById(loanInputDto.userId)
            .orElseThrow(() -> new RuntimeException("User not found"));


    if (book.getAvailableCopies() <= 0) {
        throw new IllegalArgumentException("No copies available");
    }


    book.loanOut();
    bookRepository.save(book);


    Loan loan = LoanMapper.toEntity(loanInputDto);
    loan.setBook(book);
    loan.setUser(user);

    Loan savedLoan = loanRepository.save(loan);

    return LoanMapper.toLoanOutputDto(savedLoan);
}

    public List<LoanOutputDto> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toLoanOutputDto)
                .toList();
    }


    public LoanOutputDto getLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return LoanMapper.toLoanOutputDto(loan);
    }


    public void deleteLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));


        Book book = loan.getBook();
        book.returnCopy();
        bookRepository.save(book);

        loanRepository.deleteById(id);
    }
}



