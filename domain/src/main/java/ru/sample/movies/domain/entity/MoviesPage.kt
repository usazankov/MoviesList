package ru.sample.movies.domain.entity

data class MoviesPage(

    //Номер страницы
    val page: Int? = null,

    //Список фильмов
    val results: List<Movie> = ArrayList(0),

    //Всего страниц
    val total_pages: Int? = null,

    //Количество фильмов
    val total_results: Int? = null
)