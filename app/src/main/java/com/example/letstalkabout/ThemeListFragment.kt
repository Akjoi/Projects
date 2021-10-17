package com.example.letstalkabout

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "ThemeFragment"


class ThemeListFragment : Fragment() {
    interface Callbacks {
        fun onThemeSelected(themeId: Int)
    }
    private var callbacks: Callbacks? = null
    private lateinit var themeRecyclerView:
            RecyclerView
    private var adapter: ThemeAdapter? =
        ThemeAdapter(emptyList())

    private val themeListViewModel:
            ThemeListViewModel by lazy {
        ViewModelProviders.of(this).get(ThemeListViewModel::class.java)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =
            inflater.inflate(R.layout.user_theme_list,
                container, false)
        themeRecyclerView =
            view.findViewById(R.id.theme_recycler_view) as RecyclerView
        themeRecyclerView.layoutManager =
            LinearLayoutManager(context)
        themeRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view,
            savedInstanceState)
        themeListViewModel.themeListLiveData.observe(
            viewLifecycleOwner,
            { themes ->
                themes?.let {
                    updateUI(themes.reversed())
                }
            })

    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    private fun updateUI(themes: List<Theme>) {
        adapter = ThemeAdapter(themes)
        themeRecyclerView.adapter = adapter
    }


    private inner class ThemeHolder(view: View)
        : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var theme: Theme
        private val themeTextView: TextView =
            itemView.findViewById(R.id.user_theme_text)
        private val deleteButton: ImageButton =
            itemView.findViewById(R.id.deleteThemeButton)
        private val dateTextView: TextView =
            itemView.findViewById(R.id.date)
        init {
            deleteButton.setOnClickListener(this)
        }

        fun bind(theme: Theme, number: Int) {
            this.theme = theme
            themeTextView.text = theme.theme
            dateTextView.text = (number+1).toString()
        }

        override fun onClick(view: View) {
            callbacks?.onThemeSelected(theme.id)
        }

    }

    private inner class ThemeAdapter(var
                                     themes: List<Theme>)
        : RecyclerView.Adapter<ThemeHolder>() {

        override fun onCreateViewHolder(parent:
                                        ViewGroup, viewType: Int)
                : ThemeHolder {

            val view =
                layoutInflater.inflate(R.layout.user_theme_item_list
                    , parent, false)
            return ThemeHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }
        override fun getItemCount() =
            themes.size
        override fun onBindViewHolder(holder:
                                      ThemeHolder, position: Int) {

                val theme = themes[position]
                holder.bind(theme, position)
        }
    }


    companion object {
        fun newInstance(): ThemeListFragment {
            return ThemeListFragment()
        }
    }
}