package ru.testRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testRest.model.Mailing;
import ru.testRest.model.enums.MailType;

import java.util.Optional;

public interface MailingDAO extends JpaRepository<Mailing,Long> {

    Optional<Mailing> findById(Long id);
    Optional<Mailing> findByMailTypeAndRecipientPostcodeAndRecipientAddressAndRecipientName(MailType mailType,
                                                                                  int recipientPostcode,
                                                                                  String recipientAddress,
                                                                                  String recipientName);
    Optional<Mailing> findDistinctByMailTypeAndRecipientAddress(MailType mailType,String recipientAddress);
}
