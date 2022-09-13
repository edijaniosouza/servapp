package br.com.edijanio.servapp.models.weather

data class Forecast(
    val condition: String,
    val date: String,
    val description: String,
    val max: Int,
    val min: Int,
    val weekday: String
)