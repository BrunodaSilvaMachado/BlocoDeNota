package br.com.app.blocodenotas.controller.ui.editor

import androidx.lifecycle.ViewModel
import br.com.app.blocodenotas.model.Notas

class EditorViewModel : ViewModel() {
    val nota:Notas = Notas()

    fun updateNotaData(title: String, date: String, data: String){
        nota.title = title
        nota.lastDate = date
        nota.data = data
    }

}
