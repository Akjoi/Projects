package com.heisy.randommeditation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        setCurrentFragment(MainFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.main->setCurrentFragment(MainFragment())
                R.id.achivments->setCurrentFragment(AchivmentsFragment())
                R.id.more->setCurrentFragment(MoreFragment())
                R.id.about_us->setCurrentFragment(AboutUsFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}