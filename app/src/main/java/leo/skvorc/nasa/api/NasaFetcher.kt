package leo.skvorc.nasa.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import leo.skvorc.nasa.NASA_PROVIDER_CONTENT_URI
import leo.skvorc.nasa.NasaReceiver
import leo.skvorc.nasa.framework.sendBroadcast
import leo.skvorc.nasa.handler.downloadImageAndStore
import leo.skvorc.nasa.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaFetcher(private val context: Context) {
    private  var nasaApi: NasaApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaApi = retrofit.create(NasaApi::class.java)
    }

    fun fetchItems(count: Int) {
        val request = nasaApi.fetchItems(count)

        request.enqueue(object : Callback<List<NasaItem>> {
            override fun onResponse(
                call: Call<List<NasaItem>>,
                response: Response<List<NasaItem>>
            ) {
                response?.body()?.let { populateItems(it) }
            }

            override fun onFailure(call: Call<List<NasaItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }

        })
    }

    private fun populateItems(nasaItems: List<NasaItem>) {
        GlobalScope.launch {
            val items = mutableListOf<Item>()
            nasaItems.forEach {
                val picturePath = downloadImageAndStore(context, it.url)

                val values = ContentValues().apply {
                    put(Item::title.name, it.title)
                    put(Item::explanation.name, it.explanation)
                    put(Item::picturePath.name, picturePath)
                    put(Item::date.name, it.date)
                    put(Item::read.name, false)
                }

                context.contentResolver.insert(NASA_PROVIDER_CONTENT_URI, values)
            }

            context.sendBroadcast<NasaReceiver>()
        }
    }
}