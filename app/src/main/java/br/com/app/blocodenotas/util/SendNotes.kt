package br.com.app.blocodenotas.util

object SendNotes {
    @JvmStatic
    fun send(@androidx.annotation.NonNull context: android.content.Context, sendString: String){
        val intent = android.content.Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, sendString)
        context.startActivity(intent)
        android.widget.Toast.makeText(context, br.com.app.blocodenotas.R.string.send, android.widget.Toast.LENGTH_LONG).show()
    }
}