package uz.ismoilroziboyev.footballlivescores.utils

import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData

sealed class StandingsResource {

    object Loading : StandingsResource()

    class Error(val message: String) : StandingsResource()

    class Succes(val data: ResStandingsData) : StandingsResource()
}