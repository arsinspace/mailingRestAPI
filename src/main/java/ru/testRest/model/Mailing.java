package ru.testRest.model;

import lombok.*;
import ru.testRest.model.enums.MailType;
import ru.testRest.model.enums.MailingStatus;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
@Table(name = "mailing")
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MailType mailType;
    private int recipientPostcode;
    private String recipientAddress;
    private String recipientName;
    @Enumerated(EnumType.STRING)
    private MailingStatus mailingStatus;
    private int postalOfficeId;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "mailing")
    @PrimaryKeyJoinColumn
    private MailingMovementHistory mailingMovementHistory;
}
