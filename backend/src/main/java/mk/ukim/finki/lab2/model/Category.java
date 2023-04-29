package mk.ukim.finki.lab2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;

    @Column(length = 4000)
    private String Description;

    public Category() {
    }

    public Category(String name, String description) {
        this.Name = name;
        this.Description = description;
    }
}
