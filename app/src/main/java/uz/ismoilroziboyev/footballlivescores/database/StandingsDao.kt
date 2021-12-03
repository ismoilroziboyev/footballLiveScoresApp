package uz.ismoilroziboyev.footballlivescores.database

import androidx.room.Dao
import androidx.room.Query
import retrofit2.http.GET
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData


@Dao
interface StandingsDao : BaseDao<ResStandingsData> {

    @Query("select * from resstandingsdata where id=:id")
    fun getStandingsByLeagueId(id: String): ResStandingsData?

}