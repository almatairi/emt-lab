import axios from "../custom-axios/axios";

const EBookService = {
    fetchBooks: () => {
        return axios.get("/books");
    },
    fetchCategories: () => {
        return axios.get("/categories");
    },
    fetchAuthors: () => {
        return axios.get("/authors");
    },
    deleteBooks: (id) => {
        return axios.delete(`/books/delete/${id}`);
    },
    addBook: (name, category, availableCopies, author) => {
        return axios.post("/books/add", {
            "name": name,
            "category": category,
            "availableCopies": availableCopies,
            "author": author
        });
    },
    editBook: (id, name, category, availableCopies, author) => {
        return axios.put(`/books/edit/${id}`, {
            "name": name,
            "category": category,
            "availableCopies": availableCopies,
            "author": author
        });
    },
    getBook: (id) => {
        return axios.get(`/books/${id}`);
    }
}
export default EBookService;