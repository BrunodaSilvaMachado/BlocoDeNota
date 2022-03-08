package br.com.app.blocodenotas.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import br.com.app.blocodenotas.R

class InfoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!).apply {
            setIcon(android.R.drawable.ic_menu_info_details)
            setTitle(context.getText(R.string.info))
            setMessage(arguments?.getString("INFO"))
            setNeutralButton("Ok", null)
        }.create()
    }
    companion object {

        @JvmStatic
        fun newInstance(target: Fragment, info: String) =
            InfoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("INFO", info)
                }
                setTargetFragment(target, 1)
            }
    }
}
