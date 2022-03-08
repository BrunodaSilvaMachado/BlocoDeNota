package br.com.app.blocodenotas.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.R

class TypefaceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val styleName = arrayOf(context?.getText(R.string.textNormal).toString(),
            context?.getText(R.string.textBold).toString(),
            context?.getText(R.string.textItalic).toString()
        )

        val style = intArrayOf(arguments?.getInt("STYLE")!!)

        return AlertDialog.Builder(context!!).apply {
            setIcon(R.drawable.baseline_text_format_black_18dp)
            setTitle(context.getText(R.string.style))
            setMultiChoiceItems(styleName, itemsArray(style[0])
            ) { _, which, isChecked ->
                style[0] += if (isChecked)  which else -which
                (targetFragment as TypefaceDialogListener).onTypefaceChanged(style[0])
            }
        }.create()
    }

    private fun itemsArray(style: Int): BooleanArray? {
        return when (style) {
            1 -> booleanArrayOf(false, true, false)
            2 -> booleanArrayOf(false, false, true)
            3 -> booleanArrayOf(false, true, true)
            else -> booleanArrayOf(true, false, false)
        }
    }

    interface TypefaceDialogListener {

        fun onTypefaceChanged(style: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(target: Fragment, style: Int) =
            TypefaceDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("STYLE", style)
                }
                setTargetFragment(target, 1)
            }
    }
}
