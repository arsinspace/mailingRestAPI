package ru.testRest.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postal_office")
public class PostalOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int postCode;
    private String name;
    private String recipientAddress;
    @ElementCollection
    @CollectionTable(name = "postal_office_mailings",joinColumns = @JoinColumn(name = "postal_office_id"))
    @Column(name = "mailing_id")
    private Set<Integer> mailings;
}
