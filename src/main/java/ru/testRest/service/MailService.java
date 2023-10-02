package ru.testRest.service;

import org.springframework.stereotype.Service;
import ru.testRest.model.Mailing;
import ru.testRest.model.MailingMovementHistory;
import ru.testRest.model.PostalOffice;
import ru.testRest.model.enums.MailingStatus;
import ru.testRest.repository.MailingDAO;
import ru.testRest.repository.PostalOfficeDAO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailService {

    private final MailingDAO mailingDAO;
    private final PostalOfficeDAO postalOfficeDAO;

    public MailService(MailingDAO mailingDAO, PostalOfficeDAO postalOfficeDAO) {
        this.mailingDAO = mailingDAO;
        this.postalOfficeDAO = postalOfficeDAO;
        postalOfficeDAO.save(PostalOffice.builder()
                    .name("USA")
                    .mailings(new HashSet<>())
                    .postCode(123)
                    .recipientAddress("123")
                    .build());
        postalOfficeDAO.save(PostalOffice.builder()
                        .name("EU")
                        .mailings(new HashSet<>())
                        .postCode(124)
                        .recipientAddress("124")
                .build());
    }

    public void saveMailing(Mailing mailing){
            Mailing transientMailing = Mailing.builder()
                    .mailType(mailing.getMailType())
                    .recipientPostcode(mailing.getRecipientPostcode())
                    .recipientAddress(mailing.getRecipientAddress())
                    .recipientName(mailing.getRecipientName())
                    .mailingStatus(MailingStatus.CREATED)
                    .build();
            mailingDAO.save(transientMailing);
        }

    public Mailing getMailing(Long id){
        return mailingDAO.findById(id).get();
    }
    public void arrivalAtTheIntermediatePostOffice(Long mailingId,Long postalOfficeId){
        PostalOffice transientPostalOffice = postalOfficeDAO.findById(postalOfficeId).get();
        Mailing transientMailing = mailingDAO.findById(mailingId).get();
        transientMailing.setPostalOfficeId(transientPostalOffice.getId().intValue());
        transientMailing.setMailingStatus(MailingStatus.IN_PROGRESS);
        transientPostalOffice.getMailings().add(transientMailing.getId().intValue());
        postalOfficeDAO.save(transientPostalOffice);

        MailingMovementHistory mailingMovementHistory = MailingMovementHistory.builder()
                .mailing(transientMailing)
                .arrivedToPostalOffice(transientPostalOffice.getName())
                .arrived(new Date())
                .build();
        transientMailing.setMailingMovementHistory(mailingMovementHistory);

        mailingDAO.save(transientMailing);
    }

    public void departedAtTheIntermediatePostOffice(Long mailingId,Long postalOfficeId){
        PostalOffice transientPostalOffice = postalOfficeDAO.findById(postalOfficeId).get();
        Mailing transientMailing = mailingDAO.findById(mailingId).get();
        transientPostalOffice.getMailings().remove(transientMailing.getId().intValue());
        transientMailing.getMailingMovementHistory().setDeparture(new Date());
        transientMailing.getMailingMovementHistory().setDepartedToPostalOffice("To recipient");

        postalOfficeDAO.save(transientPostalOffice);
        mailingDAO.save(transientMailing);
    }

    public void receivingMailing(Long id){
        Mailing transientMailing = mailingDAO.findById(id).get();
        transientMailing.setMailingStatus(MailingStatus.RECEIVED);
        mailingDAO.save(transientMailing);
    }

    public String getMailingHistory(Long id){
        StringBuilder history = new StringBuilder();
        Mailing transientMailing = mailingDAO.findById(id).get();
        MailingMovementHistory mailingMovementHistory = transientMailing.getMailingMovementHistory();
        history.append(mailingMovementHistory.getMailing().getMailType()).append(" - Mailing type")
                .append("\n")
                .append(mailingMovementHistory.getArrivedToPostalOffice()).append(" - ArrivedToPostalOffice")
                .append("\n")
                .append(mailingMovementHistory.getArrived()).append("- Arrived time")
                .append("\n")
                .append(mailingMovementHistory.getDepartedToPostalOffice()).append(" - DepartedToPostalOffice")
                .append("\n")
                .append(mailingMovementHistory.getDeparture()).append(" - Departed time");
        return history.toString();
    }

    public List<Mailing> getFirstPostalOfficeMailings(){
        PostalOffice transientPostalOffice = postalOfficeDAO.getById(1L);
        return transientPostalOffice.getMailings().stream()
                .map(el -> mailingDAO.getById(el.longValue()))
                .collect(Collectors.toList());
    }

    public List<Mailing> getSecondPostalOfficeMailings(){
        PostalOffice transientPostalOffice = postalOfficeDAO.getById(2L);
        return transientPostalOffice.getMailings().stream()
                .map(el -> mailingDAO.getById(el.longValue()))
                .collect(Collectors.toList());
    }

    public String getMailingStatus(Long id){
        Mailing transientMailing = mailingDAO.findById(id).get();
        return transientMailing.getMailingStatus().toString();
    }
}
