package com.project.pageflow.dto;

import com.project.pageflow.models.Author;
import com.project.pageflow.models.Genre;
import com.project.pageflow.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;

    @NotNull
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;

    public Book toBook() {

        Author author = Author.builder()
                .name(authorName)
                .email(authorEmail)
                .build();

        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .book_author(author)
                .build();
    }


}
