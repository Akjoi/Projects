package com.heisy.randommeditation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoreFragment: Fragment(R.layout.fragment_more) {
    interface Callbacks {
        fun onMeditationSelected(meditation: String)
    }
    private var callbacks: Callbacks? = null
    private lateinit var meditationRecyclerView:
            RecyclerView
    private var adapter: MeditationAdapter? =
        MeditationAdapter(emptyList())

    private val viewModel:
            MainFragmentViewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)
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
            inflater.inflate(R.layout.more_list,
                container, false)
        meditationRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        meditationRecyclerView.layoutManager =
            LinearLayoutManager(context)
        meditationRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState
        )




        updateUI(viewModel.kindsOfMeditation)
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    private fun updateUI(meditations: List<Meditation>) {
        adapter = MeditationAdapter(meditations)
        meditationRecyclerView.adapter = adapter
    }

    private inner class MeditationHolder(view: View)
        : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var meditation: Meditation
        private val name: TextView =
            itemView.findViewById(R.id.meditation)
        private val description: TextView =
            itemView.findViewById(R.id.meditation_description)
        private val arrow: ImageView =
            itemView.findViewById(R.id.arrow)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(meditation: Meditation) {
            this.meditation = meditation
            name.text = this.meditation.meditation
            description.text =
                this.meditation.description
            description.isVisible = false
        }

        override fun onClick(view: View) {
            callbacks?.onMeditationSelected(meditation.meditation)

        }
    }

    private inner class MeditationAdapter(var
                                     crimes: List<Meditation>)
        : RecyclerView.Adapter<MeditationHolder>() {

        override fun onCreateViewHolder(parent:
                                        ViewGroup, viewType: Int)
                : MeditationHolder {

            val view =
                layoutInflater.inflate(R.layout.more_list_item
                    , parent, false)
            return MeditationHolder(view)
        }
        override fun getItemCount() =
            crimes.size
        override fun onBindViewHolder(holder:
                                      MeditationHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

    }

}