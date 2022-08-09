package com.example.android.mvvmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.mvvmtest.view.CocktailSearchResultFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.cocktail_container,CocktailSearchResultFragment()).commit()
    }
}