package leo.skvorc.nasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import leo.skvorc.nasa.framework.sendBroadcast
import leo.skvorc.nasa.framework.setBooleanPreference
import leo.skvorc.nasa.framework.startActivity

class NasaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
    //    context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<LogInActivity>()
    }
}