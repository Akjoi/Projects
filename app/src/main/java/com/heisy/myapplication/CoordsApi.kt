package com.heisy.myapplication



import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers


/**
 *Интерфейс для получения данных
 */
interface CordsApi {
    // Добавляем к базовому URL оставшуюся часть
    @GET("./russia.geo.json")
    @Headers("Content-Type: application/json")
            /**
             * @return объект типа Single, содержащий CordsList
             */
    fun getData() : Single<CordsList>
}
