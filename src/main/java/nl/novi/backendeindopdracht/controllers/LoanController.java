package nl.novi.backendeindopdracht.controllers;


import nl.novi.backendeindopdracht.dto.LoanInputDto;
import nl.novi.backendeindopdracht.dto.LoanOutputDto;
import nl.novi.backendeindopdracht.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/loans")



public class LoanController {



    private final LoanService loanService;



    public LoanController(LoanService loanService) {
    this.loanService = loanService;

    }


@PostMapping("/create")
public ResponseEntity <LoanOutputDto> createLoan(@RequestBody LoanInputDto loanInputDto) {

  LoanOutputDto createdLoan = loanService.createLoan(loanInputDto);
  return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(createdLoan);

}

@GetMapping("/all")
public ResponseEntity <List <LoanOutputDto>> getAllLoans() {

     List<LoanOutputDto> loans = loanService.getAllLoans();
     return ResponseEntity.ok(loans);
}



@GetMapping("/{id}")
    public ResponseEntity <LoanOutputDto> getLoanById(@PathVariable Long id) {

        LoanOutputDto loan = loanService.getLoan(id);
        return ResponseEntity.ok(loan);


}

@DeleteMapping("/{id}")
 public ResponseEntity <Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
 }


@PatchMapping("/{id}")
    public ResponseEntity <LoanOutputDto> updateLoan(@PathVariable Long id, @RequestBody LoanInputDto loan) {

        LoanOutputDto updatedLoan = loanService.updateLoan(id, loan);
        return ResponseEntity.ok(updatedLoan);


}


@PatchMapping("/{id}/return")
    public ResponseEntity <LoanOutputDto> returnLoan(@PathVariable Long id) {


        LoanOutputDto result = loanService.returnLoan(id);
        return ResponseEntity.ok(result);
}

}
