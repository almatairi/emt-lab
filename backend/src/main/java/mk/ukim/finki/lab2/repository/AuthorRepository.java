package mk.ukim.finki.lab2.repository;

import mk.ukim.finki.lab2.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    void deletebyName(String name);
}
