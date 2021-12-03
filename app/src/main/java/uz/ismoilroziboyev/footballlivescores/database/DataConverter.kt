package uz.ismoilroziboyev.footballlivescores.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.Data

class DataConverter {

    @TypeConverter
    fun standingToJson(data: Data): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJsonToStanding(string: String): Data {
        val type = object : TypeToken<Data>() {}.type

        return Gson().fromJson(string, type)
    }


}