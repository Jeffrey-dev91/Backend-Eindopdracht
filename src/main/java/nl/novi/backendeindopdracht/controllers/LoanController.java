package nl.novi.backendeindopdracht.controllers;


import jakarta.validation.Valid;
import nl.novi.backendeindopdracht.dto.LoanInputDto;
import nl.novi.backendeindopdracht.dto.LoanOutputDto;
import nl.novi.backendeindopdracht.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    public LoanController(LoanService loanService) {
    this.loanService = loanService;
    }

@PostMapping
public ResponseEntity <LoanOutputDto> createLoan(@Valid @RequestBody LoanInputDto loanInputDto) {

  LoanOutputDto createdLoan = loanService.createLoan(loanInputDto);

    URI uri = URI.create(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdLoan.getId())
            .toUriString());

    return ResponseEntity.created(uri).body(createdLoan);
    }

@GetMapping
public ResponseEntity <List <LoanOutputDto>> getAllLoans() {
     return ResponseEntity.ok(loanService.getAllLoans());
}

@GetMapping("/{id}")
    public ResponseEntity <LoanOutputDto> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
}


@DeleteMapping("/{id}")
 public ResponseEntity <Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
 }


@PatchMapping("/{id}")
    public ResponseEntity <LoanOutputDto> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanInputDto loan) {
        return ResponseEntity.ok(loanService.updateLoan(id, loan));
}


@PatchMapping("/{id}/return")
    public ResponseEntity <LoanOutputDto> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
}
}
