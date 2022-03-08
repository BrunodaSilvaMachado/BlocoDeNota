package br.com.app.blocodenotas.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.controller.ui.editor.EditorFragment

class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        val editorFragment = EditorFragment.newInstance()
        editorFragment.arguments =  intent.getBundleExtra(Intent.ACTION_EDIT)

        if(supportFragmentManager.findFragmentByTag("EditorFragment") == null) run {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.edit_frame_layout, editorFragment, "EditorFragment").commitNow()
        }
    }

}
