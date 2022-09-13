package br.com.edijanio.servapp.repository

import android.util.Log
import br.com.edijanio.servapp.api.ChamadosWebClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

open class ChamadosRepository(
    private val webClient: ChamadosWebClient = ChamadosWebClient()
) {


    fun getWeather(){
        CoroutineScope(IO).launch {
            Log.d("retrofit", "${webClient.getAlgo().body()}")
        }
    }
}