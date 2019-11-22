package ru.sample.movies.presentation.view.utils

import ru.sample.movies.domain.entity.enums.ELanguage

class Utils {
    companion object{
        fun mapELanguage(language: ELanguage) =
            when(language){
                ELanguage.en -> "English"
                ELanguage.ru -> "Russian"
                else -> "Unknown"
            }
    }

}