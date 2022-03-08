package br.com.app.blocodenotas.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import br.com.app.blocodenotas.R


class ColorDialogFragment : DialogFragment() {

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.color_dialog_layout,container)

        val colorInt: Int = arguments!!.getInt("ARGB")
        val pickerColor = intArrayOf(
            Color.alpha(colorInt), Color.red(colorInt),
            Color.green(colorInt), Color.blue(colorInt)
        )
        val pick = view.findViewById<ImageView>(R.id.colordialoglayoutImageViewPick)
        pick.setBackgroundColor(Color.argb(
            pickerColor[0],pickerColor[1],
            pickerColor[2],pickerColor[3]
        ))

        val editColor = view.findViewById<EditText>(R.id.colordialoglayoutEditTextARGB)
        editColor.setText(String.format(
            "%02x%02x%02x%02x",
            pickerColor[0], pickerColor[1],
            pickerColor[2], pickerColor[3]
        ))
        view.findViewById<Button>(R.id.colordialoglayoutButtonOk).
            setOnClickListener {
                (targetFragment as ColorDialogListener?)?.onColorSelect(editColor.text.toString().toLong(16).toInt())
                dismiss()
            }
        val textRed: TextView = view.findViewById(R.id.colordialoglayoutTextViewR)
        val textGreen:TextView = view.findViewById(R.id.colordialoglayoutTextViewG)
        val textBlue:TextView = view.findViewById(R.id.colordialoglayoutTextViewB)

        val seekBarRed = view.findViewById<SeekBar>(R.id.colordialoglayoutSeekBarRed)
        seekBarRed.progress = pickerColor[1]
        SeekBarHelper.setColorFilter(seekBarRed,0xffff0000.toInt())
        seekBarRed.setOnSeekBarChangeListener(SeekBarHelper.pickerColorUIChange(pick,editColor,textRed,pickerColor,1))

        val seekBarGreen = view.findViewById<SeekBar>(R.id.colordialoglayoutSeekBarGreen)
        seekBarGreen.progress = pickerColor[2]
        SeekBarHelper.setColorFilter(seekBarGreen,0xff00ff00.toInt())
        seekBarGreen.setOnSeekBarChangeListener(SeekBarHelper.pickerColorUIChange(pick,editColor,textGreen,pickerColor,2))

        val seekBarBlue = view.findViewById<SeekBar>(R.id.colordialoglayoutSeekBarBlue)
        seekBarBlue.progress = pickerColor[3]
        SeekBarHelper.setColorFilter(seekBarBlue,0xff0000ff.toInt())
        seekBarBlue.setOnSeekBarChangeListener(SeekBarHelper.pickerColorUIChange(pick,editColor,textBlue,pickerColor,3))

        return view
    }

    interface ColorDialogListener {
        fun onColorSelect(argb: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(target: Fragment?, argb: Int) =
            ColorDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt("ARGB", argb)
                }
                setTargetFragment(target, 1)
            }
    }

    private class SeekBarHelper{
        companion object{
            @JvmStatic
            fun setColorFilter(seekBar: SeekBar, color: Int){
                seekBar.progressDrawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
                seekBar.progressDrawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                seekBar.thumb.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }

            @JvmStatic
            fun pickerColorUIChange(pick: ImageView, editColor: EditText, textColor: TextView, pickerColor: IntArray, index: Int) =
                object : OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        textColor.text = seekBar?.progress.toString()
                        pickerColor[index] = progress
                        pick.setBackgroundColor(Color.argb(
                            pickerColor[0], pickerColor[1],
                            pickerColor[2], pickerColor[3])
                        )
                        editColor.setText(String.format(
                            "%02x%02x%02x%02x",
                            pickerColor[0], pickerColor[1],
                            pickerColor[2], pickerColor[3]
                        ))
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }
        }

    }
}
