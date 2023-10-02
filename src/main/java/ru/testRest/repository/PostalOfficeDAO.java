package ru.testRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testRest.model.PostalOffice;

public interface PostalOfficeDAO extends JpaRepository<PostalOffice,Long> {

}
