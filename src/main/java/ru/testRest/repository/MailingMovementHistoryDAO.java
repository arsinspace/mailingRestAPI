package ru.testRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testRest.model.MailingMovementHistory;

public interface MailingMovementHistoryDAO extends JpaRepository<MailingMovementHistory,Long> {
}
