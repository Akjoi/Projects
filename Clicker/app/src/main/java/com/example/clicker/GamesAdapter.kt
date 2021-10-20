package com.example.clicker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GamesAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GamesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var gameName: TextView? = null
        var moneyGoal: TextView? = null
        var hint: TextView? = null
        private var gameButton: Button
        init {
            hint = itemView.findViewById(R.id.hint)
            moneyGoal = itemView.findViewById(R.id.moneyGoal)
            gameName = itemView.findViewById(R.id.gameName)
            gameButton = itemView.findViewById(R.id.start)
            gameButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (adapterPosition) {
                1 ->  {
                    val intent = Intent(v?.context, FirstActivity::class.java)
                    v?.context?.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.games_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.gameName?.text = games[position].name
        holder.moneyGoal?.text = games[position].moneyGoal
        holder.hint?.text = games[position].hint
    }

    override fun getItemCount() = games.size
}