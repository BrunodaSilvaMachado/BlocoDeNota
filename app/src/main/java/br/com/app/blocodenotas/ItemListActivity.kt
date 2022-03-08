package br.com.app.blocodenotas

import android.annotation.SuppressLint
import android.app.SearchManager
import android.app.SearchableInfo
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.app.blocodenotas.controller.EditorActivity
import br.com.app.blocodenotas.controller.ItemDetailFragment
import br.com.app.blocodenotas.dialog.ReminderDialogFragment
import br.com.app.blocodenotas.model.Notas
import br.com.app.blocodenotas.service.NotasService
import br.com.app.blocodenotas.util.SendNotes
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var notasService: NotasService
    private lateinit var adapter : SimpleItemRecyclerViewAdapter

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            sendEditor(Notas())
        }

        if (findViewById<FrameLayout>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val appBarLayout = findViewById<CollapsingToolbarLayout?>(R.id.toolbar_layout)
        if(appBarLayout != null){
            appBarLayout.title = getText(R.string.app_name)
        }

        val toolbar = findViewById<Toolbar?>(R.id.detail_toolbar)
        if(toolbar != null){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        val searchView = findViewById<SearchView>(R.id.search_bar)
        if (searchView != null){
            setupSearch(searchView)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        notasService = NotasService()
        adapter = SimpleItemRecyclerViewAdapter(this, notasService.findAll() as ArrayList<Notas>, twoPane)
        recyclerView.adapter = adapter
        registerForContextMenu(recyclerView)
    }

    override fun onContextItemSelected(menuItem: MenuItem): Boolean {
        val position: Int = adapter.getItemPosition()
        val item: Notas = adapter.getValues()[position]
        when (menuItem.itemId){
            R.id.item_detail_menu_edit -> sendEditor(item)
            R.id.item_detail_menu_send -> SendNotes.send(this@ItemListActivity, item.toString())
            R.id.item_detail_menu_reminder -> ReminderDialogFragment().setReminder(item).show(supportFragmentManager, "ReminderDialogFragment")
            R.id.item_detail_menu_delete -> {
                val adb = AlertDialog.Builder(this@ItemListActivity)
                adb.setIcon(R.drawable.baseline_delete_forever_black_18dp)
                    .setTitle(R.string.apaga_nota_dlg)
                    .setNegativeButton(R.string.nao, null)
                    .setPositiveButton(R.string.sim) { _: DialogInterface, _: Int ->
                        notasService.remove(item.id)
                        adapter.setValues(notasService.findAll())
                        adapter.notifyDataSetChanged()
                    }.show()
            }

        }
        return super.onContextItemSelected(menuItem)
    }
    private fun sendEditor(nota: Notas){
        startActivity(Intent(baseContext, EditorActivity::class.java).apply {
            putExtra(Intent.ACTION_EDIT, Bundle().apply {
                putParcelable(Intent.ACTION_EDIT, nota)
            })
        })
    }

    private fun setupSearch(searchView: SearchView){
        val searchManager = (getSystemService(Context.SEARCH_SERVICE) as SearchManager)
        val info: SearchableInfo? = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(info)
        searchView.onActionViewCollapsed()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter?.filter(newText)
                return false
            }
        })

    }

    inner class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                              private val values: ArrayList<Notas>,
                                              private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>(), Filterable {

        //private var values: ArrayList<Notas> = ArrayList()
        private var itemPosition: Int = 0
        private val notasFilter: Filter = object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList = ArrayList<Notas>()
                if (charString.isEmpty()) {
                    filteredList.addAll(notasService.findAll())
                } else {
                    notasService.findByText(charString)?.let { filteredList.addAll(it) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                clear()
                setValues( filterResults.values as ArrayList<Notas> )
                notifyDataSetChanged()
                if(getValues().isNotEmpty())
                    spoiler(getValues()[0].id)
            }
        }

        init {
            if (values.isNotEmpty())
                spoiler(values[0].id)
        }

        private fun spoiler(itemId: Int){
            if (twoPane) {
                val fragment = ItemDetailFragment()
                fragment.arguments = Bundle().apply { putInt(ItemDetailFragment.ARG_ITEM_ID, itemId) }
                parentActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTitleView?.text = getValues()[position].title
            holder.mDateView?.text = getValues()[position].lastDate
            holder.mPreviewView?.text = HtmlCompat.fromHtml(getValues()[position].data, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.itemView.tag = getValues()[position]
            holder.itemView.setOnClickListener { view: View ->
                val item: Notas = view.tag as Notas
                setItemPosition(position)

                if(twoPane){
                   spoiler(item.id)
                } else {
                    sendEditor(item)
                }
            }
            holder.itemView.setOnLongClickListener {
                setItemPosition(position)
                false
            }
        }

        override fun onViewRecycled(holder: ViewHolder) {
            holder.itemView.setOnLongClickListener(null)
            super.onViewRecycled(holder)
        }

        override fun getItemCount() = values.size

        fun getValues():MutableList<Notas>{
            return values
        }

        fun setValues(lvalues: List<Notas>){
            values.addAll(lvalues)
        }

        fun clear(){
            values.clear()
        }

        fun getItemPosition(): Int{
            return itemPosition
        }

        fun setItemPosition(itemPosition: Int) {
            this.itemPosition = itemPosition
        }

        override fun getFilter(): Filter? {
            return notasFilter
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
            var mTitleView: TextView? = null
            var mDateView: TextView? = null
            var mPreviewView: TextView? = null

            init {
                mTitleView = view.findViewById(R.id.title_nota)
                mDateView = view.findViewById(R.id.date_nota)
                mPreviewView = view.findViewById(R.id.preview_nota)
                view.setOnCreateContextMenuListener(this@ViewHolder)
            }

            override fun onCreateContextMenu(contextMenu: ContextMenu, view: View, contextMenuInfo: ContextMenu.ContextMenuInfo?){
                menuInflater.inflate(R.menu.item_detail_menu,contextMenu)
                contextMenu.setHeaderTitle(R.string.options)
                contextMenu.setHeaderIcon(R.drawable.baseline_build_black_24)

            }
        }
    }
}
