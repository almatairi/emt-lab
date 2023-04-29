package mk.ukim.finki.lab2.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.lab2.model.Author;
import mk.ukim.finki.lab2.model.Book;
import mk.ukim.finki.lab2.model.Category;
import mk.ukim.finki.lab2.model.dto.BookDto;
import mk.ukim.finki.lab2.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.lab2.model.exceptions.BookNotFoundException;
import mk.ukim.finki.lab2.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.lab2.repository.AuthorRepository;
import mk.ukim.finki.lab2.repository.BookRepository;
import mk.ukim.finki.lab2.repository.CategoryRepository;
import mk.ukim.finki.lab2.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Book> save(String name, Long category, Long authorId, Integer availableCopies) {
       Author author = this.authorRepository.findById(authorId)
                .orElseThrow(()-> new AuthorNotFoundException(authorId));
       Category category1 = this.categoryRepository.findById(category)
               .orElseThrow(()-> new CategoryNotFoundException(category));
       this.bookRepository.deleteByName(name);
       return Optional.of(this.bookRepository.save(new Book(name,category1,author,availableCopies)));
    }

    @Override
    public Optional<Book> edit(Long id, String name, String surname, Long countryId) {
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<Book> edit(Long id, String name, Long category, Long authorId, Integer availableCopies) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(()->new BookNotFoundException(id));
        book.setName(name);

        Category category1 = this.categoryRepository.findById(category)
                .orElseThrow(() -> new CategoryNotFoundException(category));
        book.setCategory(category1);

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(()->new AuthorNotFoundException(authorId));
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);

        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book markAsTaken(Long id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setMark(true);
        book.setAvailableCopies(book.getAvailableCopies()-1);

        return this.bookRepository.save(book);
    }



    @Override
    public Optional<Book> save(BookDto bookDto) {
        Category category=this.categoryRepository.findById(bookDto.getCategory()).orElseThrow(() -> new CategoryNotFoundException(bookDto.getCategory()));
        this.bookRepository.deleteByName(bookDto.getName());

        Author author=this.authorRepository.findById(bookDto.getAuthor()).orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));
        this.bookRepository.deleteByName(bookDto.getName());

        Book book=new Book(bookDto.getName(),category,author,bookDto.getAvailableCopies());
        this.bookRepository.save(book);

        return Optional.of(book);

    }



   @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
      Book book = this.bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
      book.setName(bookDto.getName());

        Category category=this.categoryRepository.findById(bookDto.getCategory()).orElseThrow(() -> new CategoryNotFoundException(bookDto.getCategory()));
        this.bookRepository.deleteByName(bookDto.getName());
        book.setCategory(category);

        Author author=this.authorRepository.findById(bookDto.getAuthor()).orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));
        this.bookRepository.deleteByName(bookDto.getName());
        book.setAuthor(author);
        book.setAvailableCopies(bookDto.getAvailableCopies());
        this.bookRepository.save(book);

        return Optional.of(book);
    }
}
