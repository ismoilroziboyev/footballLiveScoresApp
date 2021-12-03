package uz.ismoilroziboyev.footballlivescores.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api-football-standings.azharimm.site/"


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()
    }


    val apiService = getRetrofit().create(ApiService::class.java)

}