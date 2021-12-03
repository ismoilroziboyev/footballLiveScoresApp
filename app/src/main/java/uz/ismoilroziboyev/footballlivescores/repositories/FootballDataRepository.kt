package uz.ismoilroziboyev.footballlivescores.repositories

import kotlinx.coroutines.flow.flow
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.database.entities.LeaguesEntity
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData
import uz.ismoilroziboyev.footballlivescores.retrofit.ApiService

class FootballDataRepository(val apiService: ApiService, val appDatabase: AppDatabase) {


    suspend fun getAllLeagues() = flow { emit(apiService.getAllLeaguesData()) }

    suspend fun getAllStandings(id: String) = flow { emit(apiService.getStandings(id)) }

    suspend fun insertNewStandingsDb(data: ResStandingsData) =
        appDatabase.standingsDao().insert(data)

    suspend fun insertLeaguesDataToDb(data: List<LeaguesEntity>) =
        appDatabase.leaguesDao().insert(data)


}