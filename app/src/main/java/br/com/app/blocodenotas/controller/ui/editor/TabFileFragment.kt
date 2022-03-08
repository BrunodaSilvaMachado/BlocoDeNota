package br.com.app.blocodenotas.controller.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.dialog.InfoDialogFragment
import br.com.app.blocodenotas.util.SendNotes
import kotlinx.android.synthetic.main.fragment_tab_file.*

class TabFileFragment(target: EditorFragment) : TabFragment(target) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_file, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val nota = editorFragment.getShareNota()
        action_send.setOnClickListener { SendNotes.send(context!!, nota.toString()) }
        action_info.setOnClickListener { InfoDialogFragment.newInstance(this,
            "Title:\t ${nota?.title}\nCategory:\t ${nota?.category}\nDate:\t ${nota?.lastDate}\n" +
                    "Local:\t storage\nMimeType:\t text/html"
        ).show(activity!!.supportFragmentManager, "InfoDialog") }

    }

}
