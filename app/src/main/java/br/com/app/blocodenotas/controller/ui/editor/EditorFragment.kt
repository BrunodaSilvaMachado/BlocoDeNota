package br.com.app.blocodenotas.controller.ui.editor

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.ItemListActivity
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.model.Notas
import br.com.app.blocodenotas.service.NotasService
import br.com.app.blocodenotas.view.ShareComponentView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.edit_layout.*

class EditorFragment : Fragment(), ShareComponentView {

    companion object {
        fun newInstance() = EditorFragment()
    }

    private var nota: Notas? = null

    private fun getActionBar() {
        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nota = if (arguments != null) arguments?.getParcelable(Intent.ACTION_EDIT) else Notas()

        activity?.let{
            (it as AppCompatActivity).setSupportActionBar(toolbar)
            getActionBar()
        }

        val editorHeight: Int = context?.resources?.displayMetrics?.heightPixels ?: 640

        edit_title.setText(nota?.title)
        edit_date.text = nota?.lastDate
        editor.apply {
            setEditorHeight(editorHeight/2)
            setEditorFontSize(22)
            setEditorFontColor(Color.BLACK)
            setPadding(10,10,10,10)
            setPlaceholder(getText(R.string.insert_text_here).toString())
            html = nota?.data
        }

        val adapter = TabsAdapter(activity!!.supportFragmentManager)
        adapter.add(TabEditFragment(this@EditorFragment), getText(R.string.edit))
        adapter.add(TabTitleFragment(this@EditorFragment), getText(R.string.title))
        adapter.add(TabParagraphFragment(this@EditorFragment), getText(R.string.paragraph))
        adapter.add(TabMakersFragment(this@EditorFragment), getText(R.string.makers))
        adapter.add(TabInsertFragment(this@EditorFragment), getText(R.string.insert))
        adapter.add(TabFileFragment(this@EditorFragment), getText(R.string.file))

        tabs.setupWithViewPager(view_pager.apply { setAdapter(adapter) })
        tabs.tabGravity = TabLayout.GRAVITY_START
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sub_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> activity?.navigateUpTo(Intent(context, ItemListActivity::class.java))
            R.id.mainMenuSave -> if (NotasService().save(
                    nota?.apply {
                            id = if(this.id == 0) System.currentTimeMillis().toInt() else this.id
                            title = edit_title.text.toString()
                            data = editor.html
                    }!!
                )){
                Toast.makeText(context, R.string.salvo, Toast.LENGTH_LONG).show()
            }
            R.id.main_menu_undo -> editor.undo()
            R.id.main_menu_redo -> editor.redo()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getShareView(): View {
        return editor
    }

    fun getShareNota(): Notas? {
        return nota
    }
}
