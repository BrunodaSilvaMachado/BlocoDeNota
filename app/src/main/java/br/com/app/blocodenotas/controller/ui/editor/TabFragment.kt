package br.com.app.blocodenotas.controller.ui.editor

import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.view.ShareComponentView
import jp.wasabeef.richeditor.RichEditor
import java.lang.RuntimeException

open class TabFragment(val editorFragment: EditorFragment) : Fragment() {
    protected var editor: RichEditor? = null

    override fun onStart() {
        super.onStart()

        editor = (editorFragment as ShareComponentView).getShareView() as RichEditor
    }

    override fun onStop() {
        super.onStop()
        editor = null
    }

}