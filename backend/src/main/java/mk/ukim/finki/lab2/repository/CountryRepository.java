package mk.ukim.finki.lab2.repository;

import mk.ukim.finki.lab2.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    void deleteByName(String name);
}
