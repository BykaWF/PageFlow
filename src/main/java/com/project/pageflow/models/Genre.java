package com.project.pageflow.models;

import java.util.*;

public enum Genre {
    FICTIONAL(1),
    NON_FICTIONAL(2),
    HISTORY(3),
    FINANCE(4),
    PROGRAMMING(5),
    ENGINEERING(6),
    MATHEMATICS(7),
    GEOGRAPHY(8);

    private int id;
    private static final Map<Integer, Genre> genreById = new HashMap<>();

    static {
        for (Genre genre : Genre.values()) {
            genreById.put(genre.getId(), genre);
        }
    }

    Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Genre getGenreById(int id) {
        return genreById.get(id);
    }
}
