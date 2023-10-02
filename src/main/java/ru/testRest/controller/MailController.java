package ru.testRest.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.testRest.dto.MailingDTO;
import ru.testRest.model.Mailing;
import ru.testRest.service.MailService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;
    private final ModelMapper modelMapper;

    public MailController(MailService mailService, ModelMapper modelMapper) {
        this.mailService = mailService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/registration-mailing")
    public ResponseEntity<HttpStatus> registrationNewMailing(@RequestBody MailingDTO mailingDTO){
        mailService.saveMailing(convertToMailing(mailingDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/arrived")
    public ResponseEntity<HttpStatus> arrived(@RequestParam Long mailingId,Long postalOfficeId){
        mailService.arrivalAtTheIntermediatePostOffice(mailingId,postalOfficeId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/departed")
    public ResponseEntity<HttpStatus> departed(@RequestParam Long mailingId,Long postalOfficeId){
        mailService.departedAtTheIntermediatePostOffice(mailingId,postalOfficeId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/received/{id}")
    public ResponseEntity<HttpStatus> received(@PathVariable Long id){
        mailService.receivingMailing(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/get-mailing/{id}")
    public MailingDTO getMailing(@PathVariable Long id){
        return convertToMailingDTO(mailService.getMailing(id));
    }

    @GetMapping(value = "/get-mailing-history/{id}")
    public String getMailingHistory(@PathVariable Long id){
        return mailService.getMailingHistory(id);
    }

    @GetMapping(value = "/get-mailing-status/{id}")
    public String getMailingStatus(@PathVariable Long id){
       return mailService.getMailingStatus(id);
    }

    @GetMapping(value = "/get-first-postal-office-mailings")
    public List<MailingDTO> getFirstPostalOfficeMailings(){
        return mailService.getFirstPostalOfficeMailings().stream()
                .map(this::convertToMailingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/get-second-postal-office-mailings")
    public List<MailingDTO> getSecondPostalOfficeMailings(){
        return mailService.getSecondPostalOfficeMailings().stream()
                .map(this::convertToMailingDTO)
                .collect(Collectors.toList());
    }

    private Mailing convertToMailing(MailingDTO mailingDTO){
        return modelMapper.map(mailingDTO, Mailing.class);
    }
    private MailingDTO convertToMailingDTO(Mailing mailing){
        return modelMapper.map(mailing, MailingDTO.class);
    }
}
