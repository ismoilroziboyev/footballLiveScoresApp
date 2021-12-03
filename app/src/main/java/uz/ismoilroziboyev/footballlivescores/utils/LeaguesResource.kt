package uz.ismoilroziboyev.footballlivescores.utils

import uz.ismoilroziboyev.footballlivescores.database.entities.LeaguesEntity

sealed class LeaguesResource {

    object Loading : LeaguesResource()


    class Error(val message: String) : LeaguesResource()

    class Success(val data: List<LeaguesEntity>) : LeaguesResource()
}
