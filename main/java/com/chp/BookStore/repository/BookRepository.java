package com.chp.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.chp.BookStore.entity.BookEntity;
@EnableJpaRepositories
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

	public List<BookEntity> findByBookNameIgnoreCaseLike(String bookName);
	

}