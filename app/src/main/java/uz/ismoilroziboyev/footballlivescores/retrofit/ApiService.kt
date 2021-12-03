package uz.ismoilroziboyev.footballlivescores.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.ismoilroziboyev.footballlivescores.models.responce.ResLeagueData
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData


interface ApiService {

    @GET("leagues")
    suspend fun getAllLeaguesData(): Response<ResLeagueData>

    @GET("/leagues/{id}/standings")
    suspend fun getStandings(@Path("id") id: String): Response<ResStandingsData>


    @GET("/leagues/{id}/standings")
    fun getStandingsNoCoroutine(@Path("id") id: String): Call<ResStandingsData>
}