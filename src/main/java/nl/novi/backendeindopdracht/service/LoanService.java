package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.LoanInputDto;
import nl.novi.backendeindopdracht.dto.LoanOutputDto;
import nl.novi.backendeindopdracht.exception.BadRequestException;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
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
private final LoanMapper loanMapper;
private final BookService bookService;
private final UserService userService;




public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository, LoanMapper loanMapper, BookService bookService, UserService userService) {

    this.loanRepository = loanRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
    this.loanMapper = loanMapper;
this.bookService = bookService;
this.userService = userService;
}


public LoanOutputDto createLoan(LoanInputDto loanInputDto) {

    Book book = bookRepository.findById(loanInputDto.bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));


    User user = userRepository.findById(loanInputDto.userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));


    if (book.getAvailableCopies() <= 0) {
        throw new BadRequestException("No copies available for book" + book.getTitle());
    }

    book.loanOut();
    bookRepository.save(book);
    Loan loan = LoanMapper.toEntity(loanInputDto);
    loan.setBook(book);
    loan.setUser(user);

    if(loan.getReturnDate() == null) {
        loan.setReturnDate(loan.getLoanDate().plusWeeks(3));
    }

    Loan savedLoan = loanRepository.save(loan);
    return loanMapper.toLoanOutputDto(savedLoan);
}

    public List<LoanOutputDto> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toLoanOutputDto)
                .toList();
    }


    public LoanOutputDto getLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        return loanMapper.toLoanOutputDto(loan);
    }

    public void deleteLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Book book = loan.getBook();
        book.returnCopy();
        bookRepository.save(book);
        loanRepository.deleteById(id);
    }

    public LoanOutputDto updateLoan(Long id, LoanInputDto loanInputDto) {
    Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));

if(loanInputDto.userId!= null) {
    User user = userService.getUserEntityById(loanInputDto.userId);
    loan.setUser(user);
}

    if(loanInputDto.bookId!= null) {
        Book book = bookService.getBookEntity(loanInputDto.bookId);
        loan.setBook(book);
    }
    if(loanInputDto.loanDate!= null) {
        loan.setLoanDate(loanInputDto.loanDate);
    }

    if(loanInputDto.returnDate!= null) {
        loan.setReturnDate(loanInputDto.returnDate);
    }

    Loan savedLoan = loanRepository.save(loan);
    return loanMapper.toLoanOutputDto(savedLoan);
    }


    public LoanOutputDto returnLoan(Long id) {
    Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));

    if(!loan.isReturned()){
        loan.setReturned(true);
        Book book = loan.getBook();
        if(book!=null){
            book.returnCopy();
            bookRepository.save(book);
        }
        loan = loanRepository.save(loan);
    }
    return loanMapper.toLoanOutputDto(loan);
    }}



