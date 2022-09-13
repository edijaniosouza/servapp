package br.com.edijanio.servapp.models.weather

data class Weather(
    val `by`: String,
    val execution_time: Double,
    val from_cache: Boolean,
    val results: Results,
    val valid_key: Boolean
)