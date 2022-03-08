package br.com.app.blocodenotas.controller.ui.editor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.R
import kotlinx.android.synthetic.main.fragment_tab_title.*

/**
 * A simple [Fragment] subclass.
 */
class TabTitleFragment(target: EditorFragment) : TabFragment(target) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        action_heading1.setOnClickListener { editor?.setHeading(1) }

        action_heading2.setOnClickListener { editor?.setHeading(2) }

        action_heading3.setOnClickListener { editor?.setHeading(3) }

        action_heading4.setOnClickListener { editor?.setHeading(4) }

        action_heading5.setOnClickListener { editor?.setHeading(5) }

        action_heading6.setOnClickListener { editor?.setHeading(6) }
    }



}
