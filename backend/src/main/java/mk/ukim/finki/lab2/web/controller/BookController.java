package mk.ukim.finki.lab2.web.controller;

import mk.ukim.finki.lab2.model.Author;
import mk.ukim.finki.lab2.model.Book;
import mk.ukim.finki.lab2.model.Category;
import mk.ukim.finki.lab2.service.AuthorService;
import mk.ukim.finki.lab2.service.BookService;
import mk.ukim.finki.lab2.service.CategoryService;
import mk.ukim.finki.lab2.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/","/books"})
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CountryService countryService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, AuthorService authorService, CountryService countryService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.countryService = countryService;
        this.categoryService = categoryService;
    }
    @GetMapping
    public String getBookPage(@RequestParam(required = false) String error, Model model){
        if(error !=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Book> books = this.bookService.findAll();

        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");

        return "index";
    }

    @DeleteMapping
    public String deleteBook(@PathVariable Long id, Model model){
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping
    public String editProductPage(@PathVariable Long id, Model model){
        if(this.bookService.findById(id).isPresent()){
            Book book = this.bookService.findById(id).get();

            List<Author> authors = this.authorService.findAll();
            List<Category> categories = this.categoryService.listCategories();

            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-book");

            return "index";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping
    public String addProductPage(@PathVariable Long id, Model model){
        List<Author> authors = this.authorService.findAll();
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");

        return "index";
    }

    @PostMapping("/add")
    public String saveBook(@RequestParam(required = false) Long id,
                           @RequestParam String name,
                           @RequestParam Long category,
                           @RequestParam Long authorId,
                           @RequestParam Integer availableCopies) {
        if (id != null) {
            this.bookService.edit(id,name,category,authorId,availableCopies);
        } else {
            this.bookService.save(name,category,authorId,availableCopies);
        }
        return "redirect:/books";
    }

    @PostMapping("/marktaken/{id}")
    public String markDone(@PathVariable Long id){
        this.bookService.markAsTaken(id);
        return "redirect:/books";
    }
}
