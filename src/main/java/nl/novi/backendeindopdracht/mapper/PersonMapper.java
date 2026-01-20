//package nl.novi.backendeindopdracht.mapper;
//
//
////deze klasse zorgt ervoor dat vertalen van een dto naar een entiteit en andersom denk aan een hulpklasse.
//
//
//import nl.novi.backendeindopdracht.dto.PersonRequestDto;
//import nl.novi.backendeindopdracht.dto.PersonResponseDto;
//import nl.novi.backendeindopdracht.model.Person;
//
//public class PersonMapper {
//
//    public static Person toEntity(PersonRequestDto personRequestDto) {
//        Person person = new Person();
//
//                person.setName(personRequestDto.name);
//                person.setDob(personRequestDto.dob);
//
//                return person;
//
//    }
//public static PersonResponseDto toResponseDto(Person person) {
//        PersonResponseDto personResponseDto = new PersonResponseDto();
//
//        personResponseDto.id = person.getId();
//        personResponseDto.name = person.getName();
//        personResponseDto.dob = person.getDob();
//
//        return personResponseDto;
//
//}
//
//}
