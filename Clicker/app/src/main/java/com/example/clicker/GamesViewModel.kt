package com.example.clicker


import androidx.lifecycle.ViewModel

class GamesViewModel: ViewModel() {
    val games = listOf(
        Game("Red Light, Green Light", "100"),
        Game("Honeycomb Shape Cutting", "200"),
        Game("Tug of War", "300"),
        Game("Marble Games","400"),
        Game("Glass Bridge","500"),
        Game("Squid Game","46000000000")
    )
}