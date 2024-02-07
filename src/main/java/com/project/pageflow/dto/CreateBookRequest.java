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
    private String title;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private Genre genre;

    @NotBlank
    private String discription;

    @NotBlank
    private String imgURL;

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
                .title(this.title)
                .discription(this.discription)
                .imgURL(this.imgURL)
                .isAvaliable(this.isAvailable)
                .genre(this.genre)
                .book_author(author)
                .build();
    }


}
