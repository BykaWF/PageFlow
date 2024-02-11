package com.project.pageflow.repository;

import com.project.pageflow.models.Genre;
import com.project.pageflow.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String bookName);

    @Query("Select b from Book b, Author a where b.book_author.id = a.id and a.name = ?1")
    List<Book> findByAuthor_Name(String authorName);

    List<Book> findByGenre(Genre genre);

    @Query(value = "SELECT * FROM Book b WHERE " +
            "to_tsvector(b.title || ' ' || b.discription) @@ phraseto_tsquery(:query)",
            nativeQuery = true)
    List<Book> searchFromBar(String query);
}
