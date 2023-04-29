package mk.ukim.finki.lab2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;
    private String Surname;

    @ManyToOne
    private Country country;

    public Author(String name, String surname, Country country) {
        Name = name;
        Surname = surname;
        this.country = country;
    }
}
