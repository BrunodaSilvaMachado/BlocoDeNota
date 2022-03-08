package br.com.app.blocodenotas.controller

import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.model.Notas
import br.com.app.blocodenotas.service.NotasService

class ItemDetailFragment : Fragment() {

    private var item: Notas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notasService = NotasService()
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = notasService.findOne(it.getInt(ARG_ITEM_ID))
                setHasOptionsMenu(true)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        // Show the dummy content as text in a TextView.
        item?.let {
            val notaTitle: TextView = rootView.findViewById(R.id.title_nota)
            val notaDate: TextView = rootView.findViewById(R.id.date_nota)
            val notaText: TextView = rootView.findViewById(R.id.item_detail)

            notaTitle.text = it.title
            notaDate.text = it.lastDate
            notaText.setTextColor(it.color.toInt())
            notaText.textSize = it.textSize.toFloat()
            notaText.setTypeface(Typeface.DEFAULT, it.style)
            notaText.text = HtmlCompat.fromHtml(it.data, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return activity != null && activity!!.onContextItemSelected(item)
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
