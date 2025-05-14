package com.walfud.designpatterndemo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)

        val model = MvcModel()
        val view = MvcView(tv)
        val controller = MvcController(view, model)
        controller.initialize(tv)
    }
}