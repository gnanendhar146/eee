package com.chp.BookStore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chp.BookStore.entity.BookEntity;
import com.chp.BookStore.repository.BookRepository;
import com.chp.BookStore.services.book.BookService;


@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping ("/api/auth/book/")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	

	@GetMapping("/name/")
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }
	
	@PostMapping("/addbook")
	public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity book) {
		
		try {
			bookService.saveBook(book);
			  return ResponseEntity.ok(book);

		}catch(Exception e) {
			  return ResponseEntity.noContent().build();

		}
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<BookEntity> getBookById(@PathVariable Long id){
		try {
			BookEntity Book = bookService.getBookById(id);
			return ResponseEntity.ok(Book);
		}catch(NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteBook(@PathVariable Long id){
		try {
			bookService.deleteBook(id);
			Map<String,Boolean> response = new HashMap<>();
	    	response.put("deleted",Boolean.TRUE);
			return ResponseEntity.ok(response);
		}catch(NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<BookEntity> updateBook(@PathVariable Long id,  @RequestBody BookEntity bookDetails){
		try {
			bookService.updateBook(id,bookDetails);
			return ResponseEntity.ok(bookDetails);
		}catch(NoSuchElementException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	
	@GetMapping("/name/{bname}")
	public List<BookEntity> getBookByName(@PathVariable("bname") String bookName) {
	
		return bookService.getBookByName("%" + bookName + "%");
		
	}
	
	
	
}
