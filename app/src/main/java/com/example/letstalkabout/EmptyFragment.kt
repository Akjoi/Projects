package com.example.letstalkabout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment


class EmptyFragment: Fragment() {

    private lateinit var themeTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var deleteButton: ImageButton
    // Создаем объект крайм по запросу
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Создаем вид Crime по макету
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(
                R.layout.user_theme_item_list,
                container, false
            )
        themeTextView =
            view.findViewById(R.id.user_theme_text)
        dateTextView =
            view.findViewById(R.id.date)
        deleteButton =
            view.findViewById(R.id.deleteThemeButton)
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        updateUI()

    }

    private fun updateUI() {
        themeTextView.text = resources.getString(R.string.emptySting)
        dateTextView.text = "0"
        dateTextView.alpha = 0.4F
        themeTextView.alpha = 0.4F
        deleteButton.alpha = 0.0F
    }


    // Следующие функции описывают то, как изменяется текст когда пользователь работает с Crime

    companion object {
        fun newInstance():
                EmptyFragment{
            return EmptyFragment()
        }
    }
}