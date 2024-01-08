package com.project.pageflow.service;

import com.project.pageflow.models.Author;
import com.project.pageflow.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getOrCreate(Author author) {
        Author existingAuthor = authorRepository.findByEmail(author.getEmail());
        if (existingAuthor == null) {
            existingAuthor = authorRepository.save(author);
        }
        return existingAuthor;
    }

}
