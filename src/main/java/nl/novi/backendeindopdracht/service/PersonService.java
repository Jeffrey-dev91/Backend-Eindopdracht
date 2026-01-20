//package nl.novi.backendeindopdracht.service;
//
//
//import nl.novi.backendeindopdracht.dto.PersonRequestDto;
//import nl.novi.backendeindopdracht.dto.PersonResponseDto;
//import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
//import nl.novi.backendeindopdracht.mapper.PersonMapper;
//import nl.novi.backendeindopdracht.model.Person;
//import nl.novi.backendeindopdracht.repository.PersonRepository;
//import org.springframework.stereotype.Service;
//
////dto moet vertaald worden naar een entity doormiddel van mapper.//
//
//@Service
//public class PersonService {
//
//    private final PersonRepository repos;
//
//    public PersonService(PersonRepository repos) {
//        this.repos = repos;
//
//}
//
//
//public PersonResponseDto createPerson(PersonRequestDto personRequestDto) {
//        Person person = PersonMapper.toEntity(personRequestDto);
//        this.repos.save(person);
//
//return PersonMapper.toResponseDto(person);
//
//}
//
////find by id aanroepen,
//public PersonResponseDto getPersonById(int id){
//       return PersonMapper.toResponseDto
//               (this.repos.findById(id)
//                       .orElseThrow(()-> new ResourceNotFoundException("Person not Found"))) ;
//
//
//}
//
//
//
//}
