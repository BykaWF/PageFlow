package com.project.pageflow.repository;

import com.project.pageflow.models.Genre;
import com.project.pageflow.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM Book b WHERE " +
            "to_tsvector(b.title || ' ' || b.discription) @@ phraseto_tsquery(:query)",
            nativeQuery = true)
    List<Book> searchFromBar(String query);

    List<Book> findByGenre(Genre genre);
}
