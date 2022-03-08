package br.com.app.blocodenotas.controller.ui.editor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.blocodenotas.R
import kotlinx.android.synthetic.main.fragment_tab_makers.*

/**
 * A simple [Fragment] subclass.
 */
class TabMakersFragment(target: EditorFragment) : TabFragment(target) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_makers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        action_insert_bullets.setOnClickListener { editor?.setBullets() }

        action_insert_numbers.setOnClickListener { editor?.setNumbers() }
    }
}
