package com.walfud.designpatterndemo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // mvc
        run {
            val tvMvc = findViewById<TextView>(R.id.tv_mvc)
            val model = MvcModel()
            val view = MvcView(tvMvc)
            val controller = MvcController(view, model)
            controller.initialize(tvMvc)
        }

        // mvp(passive)
        run {
            val modal = MvpPassiveModel()
            val presenter = MvpPassivePresenter( modal)
            val tvMvpPassive = findViewById<TextView>(R.id.tv_mvp_passive)
            val view = MvpPassiveView(tvMvpPassive, presenter)
            presenter.attachView(view)
        }
    }
}