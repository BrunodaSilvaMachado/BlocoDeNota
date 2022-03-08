package br.com.app.blocodenotas.controller.ui.editor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.blocodenotas.R
import kotlinx.android.synthetic.main.fragment_tab_paragraph.*

/**
 * A simple [Fragment] subclass.
 */
class TabParagraphFragment(target: EditorFragment) : TabFragment(target) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_paragraph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        action_indent.setOnClickListener { editor?.setIndent() }

        action_outdent.setOnClickListener { editor?.setOutdent() }

        action_align_left.setOnClickListener { editor?.setAlignLeft() }

        action_align_center.setOnClickListener { editor?.setAlignCenter() }

        action_align_right.setOnClickListener { editor?.setAlignRight() }
    }


}
