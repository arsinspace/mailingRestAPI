package ru.testRest.model;

import javax.persistence.*;

import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
@Table(name = "mailing_movement_history")
public class MailingMovementHistory {
    @Id
    @Column(name = "mailing_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "mailing_id")
    private Mailing mailing;
    private String arrivedToPostalOffice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrived;
    private String departedToPostalOffice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date departure;
}
