package br.com.edijanio.servapp.api

class ChamadosWebClient(
        private val service: ChamadosService = RetrofitInstance().chamadosApiService
) {
        suspend fun getAlgo() = service.getAlgo()
}