package br.com.app.blocodenotas.environment

import android.util.Log
import java.io.*

abstract class Directory(dirname: String) {
    private val TAG = "Directory"
    private var files: File = File(dirname)

    init {
        if (!files.exists()){
            files.mkdirs()
        }
    }

    protected fun showFiles(): Array<String> {
        return if (files.exists() && files.isDirectory) files.list()!! else arrayOf()
    }

    protected fun remove(filename: String): Boolean{
        val rfile = File(files.path + File.separator + filename)

        if(!rfile.exists())
            return false
        return rfile.delete()
    }

    protected fun readObject(filename: String): Any? {
        var obj: Any? = null
        val leitura: ObjectInputStream

        try {
            leitura = ObjectInputStream(FileInputStream(files.path + File.separator + filename))
            obj = leitura.readObject()
            leitura.close()
        }catch (e: Exception ){
            Log.e(TAG, e.message, e)
        }
        return  obj
    }

    @Throws(IOException::class)
    protected open fun writeObject(o: Any?, filename: String): Void? {
        val gravacao = ObjectOutputStream(
            FileOutputStream(files.path + File.separator + filename)
        )
        gravacao.writeObject(o)
        gravacao.close()
        return null
    }
}