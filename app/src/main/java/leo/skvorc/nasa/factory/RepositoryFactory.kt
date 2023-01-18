package leo.skvorc.nasa.factory

import android.content.Context
import leo.skvorc.nasa.dao.NasaSqlHelper

fun getNasaRepository(context: Context?) = NasaSqlHelper(context)