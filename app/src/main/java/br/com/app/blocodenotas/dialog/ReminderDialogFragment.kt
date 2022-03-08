package br.com.app.blocodenotas.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.broadcast.Reminder
import br.com.app.blocodenotas.model.Notas
import java.util.*


open class ReminderDialogFragment : DialogFragment() {
    private var datePicker: DatePicker? = null
    private var timePicker: TimePicker? = null
    private var checkBox: CheckBox? = null
    private var nota: Notas = Notas()
    private val buttonListener = View.OnClickListener {
        val dateTime: Calendar = Calendar.getInstance()
        val repeat = checkBox!!.isChecked
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dateTime.set(
                datePicker!!.year, datePicker!!.month, datePicker!!.dayOfMonth,
                timePicker!!.hour, timePicker!!.minute, 0
            )
        } else {
            dateTime.set(
                datePicker!!.year, datePicker!!.month, datePicker!!.dayOfMonth,
                timePicker!!.currentHour, timePicker!!.currentMinute, 0
            )
        }
        val alarm = Reminder()
        context.let {
            if (it != null) {
                alarm.setAlarm(it, dateTime.timeInMillis, repeat, Bundle().apply { putParcelable("NOTAS", nota) })
            }
        }
        dismiss()
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = LayoutInflater.from(context).inflate(R.layout.alarm_dialog, null, false)
        view?.let {
            datePicker = it.findViewById(R.id.alarmdialogDatePicker)
            timePicker = it.findViewById(R.id.alarmdialogTimePicker)
            checkBox = it.findViewById(R.id.alarmdialogCheckBox)
            it.findViewById<Button>(R.id.alarmdialogButton).
                setOnClickListener(buttonListener)
        }

        return AlertDialog.Builder(context!!).setView(view).create()
    }

    open fun setReminder(nota: Notas): ReminderDialogFragment {
        this.nota = nota
        return this
    }
}
