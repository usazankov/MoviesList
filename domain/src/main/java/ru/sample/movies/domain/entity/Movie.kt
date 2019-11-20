package ru.sample.movies.domain.entity

import ru.sample.movies.domain.entity.enums.ELanguage
import java.util.*
import kotlin.collections.ArrayList

data class Movie(

    //Идентификатор
    val id: Int? = null,

    //Для взрослых?
    val adult: Boolean = false,

    //Жанры
    val genres: List<Genre> = ArrayList(0),

    //Язык
    val original_language: ELanguage = ELanguage.unknown,

    //Оригинальное название фильма
    val original_title: String = "",

    //Описание
    val overview: String = "",

    //Дата релиза
    val release_date: Date? = null,

    //Постер фильма
    val poster_path: String = "",

    //Популярность
    val popularity: Float? = null,

    //Название фильма
    val title: String = "",

    //Трейлер фильма
    val video: String = "",

    //Популярность
    val vote_average: Float? = null,

    //Количество голосов
    val vote_count: Int? = null
)