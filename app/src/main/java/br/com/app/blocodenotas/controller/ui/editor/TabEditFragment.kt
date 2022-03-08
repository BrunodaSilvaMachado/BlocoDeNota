package br.com.app.blocodenotas.controller.ui.editor


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.dialog.ColorDialogFragment
import br.com.app.blocodenotas.dialog.SizeDialogFragment


import kotlinx.android.synthetic.main.fragment_tab_edit.*

/**
 * A simple [Fragment] subclass.
 */
class TabEditFragment(target: EditorFragment) : TabFragment(target), ColorDialogFragment.ColorDialogListener, SizeDialogFragment.SizeDialogListener {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_tab_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        action_bold.setOnClickListener { editor?.setBold() }

        action_italic.setOnClickListener { editor?.setItalic() }

        action_subscript.setOnClickListener { editor?.setSubscript() }

        action_superscript.setOnClickListener { editor?.setSuperscript() }

        action_strikethrough.setOnClickListener { editor?.setStrikeThrough() }

        action_underline.setOnClickListener { editor?.setUnderline() }

        action_txt_color.setOnClickListener{
            activity?.let {
                ColorDialogFragment.newInstance(this@TabEditFragment, Color.BLACK).show(it.supportFragmentManager, "ColorDialog")
            }

        }

        action_bg_color.setOnClickListener(object : View.OnClickListener {
            var isChanged: Boolean = true
            override fun onClick(v:View) {
                editor?.setTextBackgroundColor(if(isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        action_font_size.setOnClickListener {
            activity?.let {
                SizeDialogFragment.newInstance(this@TabEditFragment, 22).show(it.supportFragmentManager, "SizeDialog")
            }
        }

    }

    override fun onColorSelect(argb: Int) {
        editor?.setTextColor(argb)
        editorFragment.getShareNota()?.color = argb.toLong()
    }

    override fun onSizeChanged(size: Int) {
        editor?.setFontSize(size)
        editorFragment.getShareNota()?.textSize = size
    }

}
