package com.chp.BookStore.services.book;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chp.BookStore.entity.BookEntity;
import com.chp.BookStore.repository.BookRepository;

@Service
public class BookService {


	@Autowired
	private BookRepository bookRepository;
	
	
	
	public BookEntity saveBook(BookEntity book) {
		  return bookRepository.save(book);
			
		}
	
	
	public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

	
	public BookEntity getBookById(Long bookId) {
		return bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
	}
	
	
	public void deleteBook(Long bookId) {
		BookEntity Book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
		bookRepository.delete(Book);
	}
	
	
	
   public BookEntity updateBook(Long bookId , BookEntity bookDetails) {
		
		BookEntity existingBook = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
		
		existingBook.setBookName(bookDetails.getBookName());
		existingBook.setAuthor(bookDetails.getAuthor());
		existingBook.setBookPrice(bookDetails.getBookPrice());
		
		return bookRepository.save(existingBook);
	}

	
   public List<BookEntity> getBookByName(String bookName) {
		
		return bookRepository.findByBookNameIgnoreCaseLike("%"+ bookName + "%");
	}
	
	
	
}
