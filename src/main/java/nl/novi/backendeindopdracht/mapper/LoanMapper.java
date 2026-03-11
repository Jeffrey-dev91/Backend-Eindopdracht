package nl.novi.backendeindopdracht.mapper;


import nl.novi.backendeindopdracht.dto.LoanInputDto;
import nl.novi.backendeindopdracht.dto.LoanOutputDto;
import nl.novi.backendeindopdracht.models.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component("customLoanMapper")


public class LoanMapper {

private LoanMapper() {

}


public static Loan toEntity (LoanInputDto loanInputDto) {

    Loan loan = new Loan();


    loan.setLoanDate(
            loanInputDto.loanDate != null ? loanInputDto.loanDate : LocalDate.now()
    );


    loan.setReturned(false);
    return loan;

}


public LoanOutputDto toLoanOutputDto (Loan loan) {

LoanOutputDto loanOutputDto = new LoanOutputDto();

loanOutputDto.id = loan.getId();
loanOutputDto.username = loan.getUser().getUsername();
loanOutputDto.bookTitle = loan.getBook().getTitle();
loanOutputDto.loanDate = loan.getLoanDate();
loanOutputDto.returnDate = loan.getReturnDate();
loanOutputDto.returned = loan.isReturned();
return loanOutputDto;

}

}
