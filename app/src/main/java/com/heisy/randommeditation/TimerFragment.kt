package com.heisy.randommeditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text


class TimerFragment : Fragment(R.layout.fragment_timer) {
    private var time: Double = 0.0
    private var kindOfMeditation = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle? = arguments
        if (bundle != null) {
            time = bundle.getDouble("time")
            Log.i("Timer", time.toString())
            kindOfMeditation = bundle.getString("meditation").toString()
        }
        val meditation: TextView = view.findViewById(R.id.timerMeditation)
        val timer: TextView = view.findViewById(R.id.time)
        meditation.text = kindOfMeditation
        timer.text = time.toString()
        object : CountDownTimer((time*60000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timer.text = ((millisUntilFinished/60000).toInt().toString() +
                            ":" + ((millisUntilFinished%60000)/1000).toInt().toString())
                    //here you can have your logic to set text to edittext
                }
                override fun onFinish() {
                    timer.text = "finished"
                    MediaPlayer.create(activity,R.raw.finish_sound).start()
                }
            }.start()
    }
}