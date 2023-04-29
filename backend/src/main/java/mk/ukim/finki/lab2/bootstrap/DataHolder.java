package mk.ukim.finki.lab2.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mk.ukim.finki.lab2.service.AuthorService;
import mk.ukim.finki.lab2.service.BookService;
import mk.ukim.finki.lab2.service.CategoryService;
import mk.ukim.finki.lab2.service.CountryService;
import org.springframework.stereotype.Component;



@Component
@Getter
public class DataHolder {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CountryService countryService;

    public DataHolder(AuthorService authorService, BookService bookService, CategoryService categoryService, CountryService countryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.countryService = countryService;
    }

    @PostConstruct
    void initData(){
        this.countryService.save("USA", "America");
        this.countryService.save("UK", "Europe");
        this.countryService.save("UK", "Europe");

        this.authorService.save("George", "Orwell", 1l);
        this.authorService.save("William", "Shakespeare", 2l);
        this.authorService.save("Ismail", "Kadare", 4l);

        this.categoryService.create("NOVEL","NOVEL");
        this.categoryService.create("CLASSICS", "CLASSICS");
        this.categoryService.create("THRILLER", "THRILLER");

        this.bookService.save("Animal Farm",1l,1l,13);
        this.bookService.save("Romeo and Juliette", 23l,2l,10);
        this.bookService.save("Kronik ne gure", 4l,2l,50);
    }
}
