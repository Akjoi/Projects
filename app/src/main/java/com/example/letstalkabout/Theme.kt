package com.example.letstalkabout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Theme(@PrimaryKey(autoGenerate = true) val id: Int=0,
                 val theme: String = "",
                 val tag: Int = 0)