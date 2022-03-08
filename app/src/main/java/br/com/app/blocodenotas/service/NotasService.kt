package br.com.app.blocodenotas.service

import android.util.Log
import br.com.app.blocodenotas.exception.NotFoundException
import br.com.app.blocodenotas.model.Notas
import br.com.app.blocodenotas.repositoryImpl.RwdRepositoryImpl
import java.io.IOException


open class NotasService {
    private val TAG = "NotasService"
    private val notasRepository: RwdRepositoryImpl = RwdRepositoryImpl()

    open fun findAll(): List<Notas>{
        val notasList = notasRepository.readAll()

        if (notasList.isEmpty()){
            Log.w(TAG, "Nenhuma nota foi encontrada!", NotFoundException())
        }

        return notasList
    }

    open fun findOne(id :Int): Notas{
        val notas :Notas? = notasRepository.read(id)

        if (notas == null){
            Log.w(TAG, NotFoundException("A anotação não foi encontrada"))
        }

        return notas!!
    }

    open fun findByText(keyword: String): List<Notas>? {
        val findNotes: MutableList<Notas> = ArrayList()
        for (nota in notasRepository.readAll()) {
            if (nota.title.contains(keyword) ||
                nota.data.contains(keyword)
            ) {
                findNotes.add(nota)
            }
        }
        return findNotes
    }

    open fun listByTitle(): List<String>? {
        val titleList: MutableList<String> = ArrayList()
        for (n in notasRepository.readAll()) {
            titleList.add(n.title)
        }
        return titleList
    }


    open fun listByData(): List<String>? {
        val dataList: MutableList<String> = ArrayList()
        for (n in notasRepository.readAll()) {
            dataList.add(n.data)
        }
        return dataList
    }

    open fun save(nota: Notas): Boolean {
        try {
            notasRepository.write(nota)
        } catch (e: IOException) {
            Log.e(TAG, e.message, e)
            return false
        }
        return true
    }

    open fun remove(id: Int) {
        notasRepository.delete(id)
    }

}