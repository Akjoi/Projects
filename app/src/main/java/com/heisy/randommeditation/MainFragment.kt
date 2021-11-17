package com.heisy.randommeditation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_main.*
import org.w3c.dom.Text


class MainFragment:Fragment(R.layout.fragment_main) {
    // Контейнер, который анимируется после нажатия кнопки random
    private lateinit var animatedContainer: LinearLayout
    // Подробное описание медитации
    private lateinit var meditationDescription:TextView
    // Время, которое передаётся на таймер
    var time = 0.0
    // Вид медитации
    var meditationKind = ""
    // TextView для вида медитации
    private lateinit var meditationTextView: TextView
    private lateinit var model: MainFragmentViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        // ViewModel


        // Контейнер, который при кнопке рандом выйдет
        animatedContainer = view.findViewById(R.id.meditation_description_layout)
        // Делаем его вначале невидимым
        animatedContainer.isVisible = false
        // Кнопка рандома вида медитации и времени
        val random: Button = view.findViewById(R.id.random)
        // Кнопка старт
        val start: Button = view.findViewById(R.id.start)
        // Вид медитации
        meditationTextView = view.findViewById(R.id.meditation)
        meditationTextView.text = "Вид медитации"
        // Описание медитации
        meditationDescription = view.findViewById(R.id.meditation_description)
        // Время таймера
        val timer: TextView = view.findViewById(R.id.timer)
        // Попытки
        val attempt: TextView = view.findViewById(R.id.attempt)
        timer.text = "Время"

        random.setOnClickListener {
            animation()
//            if (model.attempt > 2) {
//
//            }
//            // Анимация появления контейнера с анимацией

        }
        start.setOnClickListener {
            val timerFragment = TimerFragment()
            val bundle = Bundle()
            bundle.putDouble("time", time)
            bundle.putString("meditation", meditationKind)
            timerFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.flFragment, timerFragment)
                addToBackStack(null)
                commit()
            }
        }
    }
    private fun animation(){
        val show = AnimationUtils.loadAnimation( context,R.anim.show)
        animatedContainer.startAnimation(show)
        animatedContainer.isVisible = true

        // Переустанавливаем значении
        meditationKind = model.kindsOfMeditation.random()
        meditationTextView.text = meditationKind
        meditationDescription.text = meditationKind.repeat(10)
        time = (1..2).random().toDouble()
        timer.text = time.toString()
    }
}