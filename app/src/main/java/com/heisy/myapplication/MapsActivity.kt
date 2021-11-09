package com.heisy.myapplication

import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.heisy.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.PolylineOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Activity получает координаты в виде GeoJSON.
 * Рисует по ним маршрует на карте. Считает длину маршрута
 * Я не уверен, что я правильно посчитал длину. На вики суммарная длина границы
 * России ~60 000 км. У меня результат ~95 000. Но сделал, как смог.
 * Буду рад, если дадите фидбек.
 * @property sum Длина маршрута
 * @property distance TextView для отображения длины маршрута
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private val compositeDisposable = CompositeDisposable()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // Длина маршрута
    private var sum = 0f

    // TextView для отображения длины маршрута
    private lateinit var distance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        distance = findViewById(R.id.distance)

        // Вызваем функцию запроса
        getDataFromUrl((application as CordsApp).cordsApi)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // Двигаем камеру на Россию
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(47.917491082000055,45.63275788000014)))
    }
    /**
     * Функция преобразовывает массив парных координат в рабочий для
     * Google Maps вид. В GeoJSON сначала идёт долгота, а потом широта. Нужно наоборот
     * @param cords Список парных координат из GeoJSON
     * @return Список парных координат, который потом передаются в фукнцию makePolyline
     */
    private fun addCords(cords: List<List<Double>>): MutableList<LatLng> {
        val coordinates = mutableListOf<LatLng>()
        for (cord in cords){
            val latLng = LatLng(cord[1],cord[0])
            coordinates.add(latLng)
        }
        return coordinates
    }

    /**
     * Функция подсчёта длины PolylineOptions
     * @param polylineOptions Линия, которая чертится по LatLng
     * @return Длина маршрута
     */
    private fun getDistance(polylineOptions: PolylineOptions): Float {

        // Данное решение нашёл на stackOverFlow
        val latlongs: List<LatLng> = polylineOptions.points
        val size = latlongs.size - 1
        val results = FloatArray(1)
        var sum = 0f

        for (i in 0 until size) {
            // Функция считает расстояние между двумя координатами
            Location.distanceBetween(
                latlongs[i].latitude,
                latlongs[i].longitude,
                latlongs[i + 1].latitude,
                latlongs[i + 1].longitude,
                results
            )
            sum += results[0]
        }
        return sum
    }

    /**
     * Функция настраивает PolylineOptions по координатам для прорисовки
     * @param cords парные координат для PolylineOptions
     * @return Настроенный PolylineOptions
     */
    private fun makePolyline(cords: List<List<Double>>): PolylineOptions {
        //        sum += getDistance(polylineOptions)
//        mMap.addPolyline(
//            polylineOptions)
        return PolylineOptions()
            .addAll(addCords(cords))
            .width(5f)
            .color(Color.RED)
    }


    /**
     * Фукнция получения GeoJSON
     * Тут же отрисовывается маршруте
     * Тут же считаем общую длину маршрута и ставим её в TextView
     * Если запрос не прошёл, то прокидываем исключение + сообщаем об этом пользователю
     * @param cordsApi Настроеный CordsApi из CordsApp
     */
    private fun getDataFromUrl (cordsApi: CordsApi){
        // Если cordsApi не пустой
        cordsApi.let {
            // Настраиваем compositeDisposable
            compositeDisposable.add(cordsApi.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // Здесь получаем CordsList и обрабатываем его
                    // Перебор 213 списков
                    for (i in 0 until it.features?.first()?.geometry?.coordinates?.size!!) {
                        // Для каждого списка
                        val cords = it.features?.first()?.geometry?.coordinates?.get(i)?.get(0)
                        // Если он не пуст
                        if (cords != null) {
                            // Делаем polylineOptions
                            val polyline = makePolyline(cords)
                            // Рисуем её на карте
                            mMap.addPolyline(polyline)
                            // Считаем её длину
                            sum += getDistance(polyline)
                        }
                    }
                    // После суммарную длину отображаем на UI
                    distance.text = ((sum/1000).toInt().toString() + "  Км")
                },
                    {
                        // Если что-то пошло не так
                        // Побрасываем ошибку и сообщеаем об этом пользователю
                        // Очищаем compositeDisposable
                        distance.text = "Не удалось построить маршрут"
                        compositeDisposable.clear()
                    })
            )}
    }
}


