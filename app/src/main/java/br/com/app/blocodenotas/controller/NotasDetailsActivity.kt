package br.com.app.blocodenotas.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.text.HtmlCompat
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.broadcast.Reminder
import br.com.app.blocodenotas.model.Notas
import kotlinx.android.synthetic.main.notas_details_layout.*

class NotasDetailsActivity : AppCompatActivity() {

    private var nota: Notas? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.notas_details_layout)

        supportActionBar?.hide()

        nota = intent!!.getBundleExtra(Reminder.BUNDLE_NOTAS)?.getParcelable(Reminder.NOTAS) ?: Notas()

        cardconfirmdialoglayoutTitle.text = nota!!.title
        cardconfirmdialoglayoutNotas.setText(HtmlCompat.fromHtml(nota!!.data,HtmlCompat.FROM_HTML_MODE_LEGACY))
        cardconfirmdialoglayoutButtonPutoff.setOnClickListener {
            val putoffTime = System.currentTimeMillis() + 1000 * 60 * 5L
            Reminder().setAlarm(this@NotasDetailsActivity,putoffTime,false,Bundle().apply {
                putParcelable(Reminder.BUNDLE_NOTAS, nota)
            })
            Toast.makeText(this@NotasDetailsActivity, R.string.putoff_time, Toast.LENGTH_LONG).show()
            finish()
        }
        cardconfirmdialoglayoutButtonCompleted.setOnClickListener {
            Reminder().cancelAlarm(this@NotasDetailsActivity)
            finish()
        }

    }
}
