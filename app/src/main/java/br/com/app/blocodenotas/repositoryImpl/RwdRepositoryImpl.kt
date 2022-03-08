package br.com.app.blocodenotas.repositoryImpl

import android.os.Environment
import br.com.app.blocodenotas.environment.Directory
import br.com.app.blocodenotas.model.Notas
import br.com.app.blocodenotas.repository.RwdRepository

class RwdRepositoryImpl : Directory(
    Environment.getDataDirectory().path +
            "/data/br.com.app.blocodenotas/files/"
), RwdRepository <Notas, Int> {

    override fun read(id: Int): Notas {
        return filenameMapId(showFiles())[id]?.let { readObject(it) } as Notas
    }

    override fun readAll(): MutableList<Notas> {
        val notasMap: HashMap<String, Notas> = filenameMapNotas(showFiles())
        return ArrayList(notasMap.values)
    }

    override fun write(m: Notas) {
        writeObject(m,
            if (filenameMapId(showFiles())[m.id] != null) filenameMapId(showFiles())[m.id].toString()
            else "notes_" + System.currentTimeMillis() + ".ser"
        )
    }

    override fun delete(id: Int) {
        filenameMapId(showFiles())[id]?.let { remove(it) }
    }

    private fun filenameMapNotas(stringArray: Array<String>): HashMap<String, Notas>{
        val notasMap: HashMap<String, Notas> = HashMap()
        stringArray.forEach { notasMap[it] = readObject(it) as Notas }

        return notasMap
    }

    private fun filenameMapId(stringArray: Array<String>): HashMap<Int, String> {
        val mapId: HashMap<Int, String> = HashMap()
        for (s in stringArray) {
            mapId[(readObject(s) as Notas?)!!.id] = s
        }
        return mapId
    }
}