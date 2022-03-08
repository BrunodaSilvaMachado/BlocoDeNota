package br.com.app.blocodenotas.repository

import java.io.IOException

interface RwdRepository <M, ID> {
    @Throws(IOException::class, ClassNotFoundException::class)
    fun read(id: ID): M

    fun readAll(): MutableList<M>

    @Throws(IOException::class, ClassNotFoundException::class)
    fun write(m: M)

    @Throws(IOException::class)
    fun delete(id: ID)

}