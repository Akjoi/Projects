package com.heisy.randommeditation

import androidx.lifecycle.ViewModel

class MainFragmentViewModel: ViewModel() {
    val kindsOfMeditation = listOf("1 вид", "2 вид", "3 вид", "4 вид", "5 вид")
    var attempt = 4
}