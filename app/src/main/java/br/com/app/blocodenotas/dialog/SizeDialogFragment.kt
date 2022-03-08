package br.com.app.blocodenotas.dialog

import android.app.Dialog
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TableLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.R

class SizeDialogFragment : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progress: Int = arguments!!.getInt("PROGRESS")
        val mText = TextView(context)
        mText.apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setTypeface(typeface, Typeface.BOLD)
            text = (context.getText(R.string.size).toString() + ": $progress")
        }

        val seekBar = SeekBar(context)
        seekBar.apply {
            max = 72
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) min = 10
            this.progress = progress
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    (targetFragment as SizeDialogListener?)?.onSizeChanged(progress)
                    mText.text = (context.getText(R.string.size).toString() + ": $progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        }

        val tableLayout = TableLayout(context)
        tableLayout.apply {
            layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER
            addView(mText)
            addView(seekBar)
        }

        return AlertDialog.Builder(context!!).apply{
            setIcon(R.drawable.baseline_format_size_black_18dp)
            setTitle(context.getText(R.string.size))
            setView(tableLayout)
        }.create()
    }

    interface SizeDialogListener {
        fun onSizeChanged(size: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(target: Fragment, progress: Int) =
            SizeDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("PROGRESS", progress)
                }
                setTargetFragment(target, 1)
            }
    }
}
