package br.com.app.blocodenotas.controller.ui.editor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app.blocodenotas.R
import kotlinx.android.synthetic.main.fragment_tab_insert.*

/**
 * A simple [Fragment] subclass.
 */
class TabInsertFragment(target: EditorFragment) : TabFragment(target) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        action_blockquote.setOnClickListener { editor?.setBlockquote() }

        action_insert_image.setOnClickListener { editor?.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
            "dachshund", 320) }

        action_insert_youtube.setOnClickListener { editor?.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA") }

        action_insert_audio.setOnClickListener { editor?.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3")}

        action_insert_video.setOnClickListener { editor?.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360)}

        action_insert_link.setOnClickListener { editor?.insertLink("https://github.com/wasabeef", "wasabeef") }

        action_insert_checkbox.setOnClickListener { editor?.insertTodo() }
    }

}
