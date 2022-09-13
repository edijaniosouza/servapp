package br.com.edijanio.servapp.api

import br.com.edijanio.servapp.models.weather.Weather
import retrofit2.Response
import retrofit2.http.GET

interface ChamadosService {

    @GET("/weather")
    suspend fun getAlgo() : Response<Weather>
}