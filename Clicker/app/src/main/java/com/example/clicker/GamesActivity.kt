package com.example.clicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider

class GamesActivity: AppCompatActivity() {
    private val gamesViewModel:
            GamesViewModel by lazy {
        ViewModelProvider(this).get(GamesViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.games)
        val games = gamesViewModel.games
        games[0].hint = "you have already played"
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GamesAdapter(games)
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, GamesActivity::class.java)
        }
    }
}
