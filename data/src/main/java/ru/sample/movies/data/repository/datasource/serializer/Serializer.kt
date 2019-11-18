package ru.sample.movies.data.repository.datasource.serializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Serializer @Inject constructor(){
    private val gson: Gson = GsonBuilder()
        .create();

    fun serialize(obj: Any, type: Type): String{
        return gson.toJson(obj, type)
    }

    fun <T> deserialize(str: String, type: Type): T{
        return gson.fromJson(str, type);
    }
}