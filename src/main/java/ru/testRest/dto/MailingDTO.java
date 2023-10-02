package ru.testRest.dto;

import lombok.*;
import ru.testRest.model.enums.MailType;

@Data
public class MailingDTO {

    private MailType mailType;
    private int recipientPostcode;
    private String recipientAddress;
    private String recipientName;
}
