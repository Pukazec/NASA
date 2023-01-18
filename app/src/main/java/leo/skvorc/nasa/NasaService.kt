package leo.skvorc.nasa

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import leo.skvorc.nasa.api.NasaFetcher
import leo.skvorc.nasa.framework.sendBroadcast

private const val JOB_ID = 1

class NasaService : JobIntentService(){
    override fun onHandleWork(intent: Intent) {
        NasaFetcher(this).fetchItems(10)
    }

    companion object {
        fun enqueue(context: Context) {
            enqueueWork(
                context,
                NasaService::class.java,
                JOB_ID,
                Intent(context, NasaService::class.java)
            )
        }
    }

}