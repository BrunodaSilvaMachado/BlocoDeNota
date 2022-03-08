package br.com.app.blocodenotas.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.Date

@Parcelize
data class Notas(
    var id: Int = 0,
    var title: String = "Sem Titulo",
    var data: String = "",
    var color: Long = 0xff000000L,
    var style: Int = 0,
    var textSize: Int = 18,
    var lastDate: String = Date().toString(),
    var category: String = "Geral") : Serializable, Parcelable {
    @IgnoredOnParcel
    val serialVersionUID = 2577433209662521063L

    override fun toString(): String {
        return "$title\t\t$category\n$lastDate\n$data"
    }
}