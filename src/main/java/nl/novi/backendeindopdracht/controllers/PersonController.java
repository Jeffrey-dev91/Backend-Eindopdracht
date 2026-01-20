package nl.novi.backendeindopdracht.controllers;

//import nl.novi.backendeindopdracht.dto.PersonRequestDto;
//import nl.novi.backendeindopdracht.dto.PersonResponseDto;
//import nl.novi.backendeindopdracht.model.Person;
//import nl.novi.backendeindopdracht.repository.PersonRepository;
//import nl.novi.backendeindopdracht.service.PersonService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/persons")
//public class PersonController {
//
//
//    private final PersonService service;
//
//    public PersonController(PersonService service) {
//        this.service = service;
//
//}
//
//
////gebruik input dto en outpot dto met daarvoor verschillende entiteiten
//
//    //@valid wordt er validation gedaan voor requestdto object
//    @PostMapping
//    public ResponseEntity<PersonResponseDto> createPerson(@Valid @RequestBody PersonRequestDto personRequestDto) {
//
//       PersonResponseDto personResponseDto = this.service.createPerson(personRequestDto);
//
//        return new ResponseEntity<>(personResponseDto, HttpStatus.CREATED);
//
//    }
//
////    @GetMapping
////    public ResponseEntity<List<Person>> getaAllPersons() {
////
////        return ResponseEntity.ok(this.repos.findAll());
////    }
//
//    @GetMapping("/{id}")
//
//    public ResponseEntity<PersonResponseDto> getPersonById(@PathVariable int id) {
//
//return ResponseEntity.ok(this.service.getPersonById(id));
//
//    }
//
////    @GetMapping("/after")
////
////    public ResponseEntity<List<Person >> getPersonByDobAfter(@RequestParam LocalDate date) {
////
////        return ResponseEntity.ok(this.repos.findByDobAfter(date));
////    }
//
//}

