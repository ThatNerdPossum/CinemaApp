package com.example.mycinema.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycinema.R
import com.example.mycinema.ui.fragments.CategoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CategoryFragment())
            .commit()


        /*bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CartFragment())
                        .commit()
                    true
                }
                else -> false
            }*/
    }

}