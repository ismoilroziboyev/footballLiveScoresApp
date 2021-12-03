package uz.ismoilroziboyev.footballlivescores.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.database.entities.LeaguesEntity
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData
import uz.ismoilroziboyev.footballlivescores.repositories.FootballDataRepository
import uz.ismoilroziboyev.footballlivescores.retrofit.ApiClient
import uz.ismoilroziboyev.footballlivescores.utils.LeaguesResource
import uz.ismoilroziboyev.footballlivescores.utils.NetworkHelper
import uz.ismoilroziboyev.footballlivescores.utils.StandingsResource

class ViewModel(val appDatabase: AppDatabase, val networkHelper: NetworkHelper) : ViewModel() {

    private val footballDataRepository =
        FootballDataRepository(ApiClient.apiService, appDatabase)
    private val leaguesData = MutableStateFlow<LeaguesResource>(LeaguesResource.Loading)
    private val standingsData = MutableStateFlow<StandingsResource>(StandingsResource.Loading)


    fun getStandingsData() = standingsData
    fun getLeagueData() = leaguesData

    fun fetchLeaguesData() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                footballDataRepository.getAllLeagues().catch {
                    leaguesData.emit(LeaguesResource.Error(it.message ?: ""))
                }.collect {
                    if (it.isSuccessful) {
                        val list = ArrayList<LeaguesEntity>()
                        it.body()?.data?.forEach { data ->
                            list.add(LeaguesEntity(data.id,
                                data.name,
                                data.abbr,
                                data.logos.light))
                        }
                        leaguesData.emit(LeaguesResource.Success(list))
                        footballDataRepository.insertLeaguesDataToDb(list)
                    } else {
                        leaguesData.emit(LeaguesResource.Error(it.message() ?: ""))
                    }
                }
            } else {
                val list = appDatabase.leaguesDao().getAllLeaguesData()
                if (list.isEmpty()) {
                    leaguesData.emit(LeaguesResource.Error("No internet connection"))
                } else {
                    leaguesData.emit(LeaguesResource.Success(list))
                }
            }
        }
    }


    fun fetchStandingsData(id: String) {

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                footballDataRepository.getAllStandings(id).catch {
                    standingsData.emit(StandingsResource.Error(it.message ?: ""))
                }.collect {
                    if (it.isSuccessful) {
                        val standingModel =
                            ResStandingsData(data = it.body()?.data!!,
                                status = it.body()!!.status)

                        footballDataRepository.insertNewStandingsDb(standingModel)
                        standingsData.emit(StandingsResource.Succes(it.body()!!))
                    } else {
                        standingsData.emit(StandingsResource.Error(it.message() ?: ""))
                    }
                }
            } else {
                val data: ResStandingsData? =
                    appDatabase.standingsDao().getStandingsByLeagueId(id)

                if (data != null) {
                    standingsData.emit(StandingsResource.Succes(data))
                } else {
                    standingsData.emit(StandingsResource.Error("No internet connection!"))
                }
            }
        }
    }
}
