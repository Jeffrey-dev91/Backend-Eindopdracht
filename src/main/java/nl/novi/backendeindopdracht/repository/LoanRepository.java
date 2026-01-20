package nl.novi.backendeindopdracht.repository;


import nl.novi.backendeindopdracht.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    

}
