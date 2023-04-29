import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import Books from '../Books/BookList/books';
import EBookService from "../repository/bookRepository";
import Categories from "../Categories/categories";
import Header from '../Header/header';
import BookAdd from '../Books/BookAdd/bookAdd';
import Authors from '../Authors/authors';
import BookEdit from "../Books/BookEdit/bookEdit";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      books: [],
      categories: [],
      authors:[],
      selectedBook:{}
    }
  }

  render() {

    return (
        <Router>
          <Header/>
          <main>
            <div className="container">
              <Route path={"/categories"} exact
                     render={() => <Categories categories={this.state.categories}/>}/>
              <Route path={"/authors"} exact
                     render={() => <Authors authors={this.state.authors}/>}/>
              <Route path={"/books"} exact render={() =>
                  <Books books={this.state.books}
                         onDelete={this.deleteBooks}
                         onEdit={this.getBook}/>}/>
              <Route path={"/books/add"} exact render={()=>
                  <BookAdd categories={this.state.categories}
                           authors={this.state?.authors}
                           onAddBook={this.addBook}/>}/>
              <Route path={"/books/edit/:id"} exact render={()=>
                  <BookEdit categories={this.state.categories}
                            authors={this.state?.authors}
                            onEditBook={this.editBook}
                            book={this.state.selectedBook}/>}/>

              <Redirect to={"/books"}/>
            </div>
          </main>
        </Router>
        // <div>
        //     <Books books={this.state.books}/>
        // </div>
    );
  }

  loadBooks = () => {
    EBookService.fetchBooks()
        .then((data) => {
          this.setState({
            books: data.data
          })
        });
  }
  loadCategories = () => {
    EBookService.fetchCategories()
        .then((data) => {
          this.setState({
            categories: data.data
          })
        });
  }
  loadAuthors = () => {
    EBookService.fetchAuthors()
        .then((data) => {
          this.setState({
            authors: data.data
          })
        });
  }

  componentDidMount() {
    this.loadBooks();
    this.loadCategories();
    this.loadAuthors();
  }

  deleteBooks = (id) =>{
    EBookService.deleteBooks(id)
        .then(()=>{
          this.loadBooks();
        })
  }

  addBook=(name, category, availableCopies, author)=>{
    EBookService.addBook(name, category, availableCopies, author)
        .then(()=>{
          this.loadBooks();
        })
  }

  getBook = (id) =>{
    EBookService.getBook(id)
        .then((data)=>{
          this.setState({
            selectedBook:data.data
          })
        })
  }

  editBook = (id,name, category, availableCopies, author) => {
    EBookService.editBook(id,name, category, availableCopies, author)
        .then(()=>{
          this.loadBooks();
        })
  }

}

export default App;