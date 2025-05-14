package com.walfud.designpatterndemo

import android.view.View
import android.widget.TextView
import kotlin.random.Random

/**
 * 非常轻薄, 只负责:
 *  1. 连接 View 和 Model.
 *  2. 注册外部事件后调用 Model 触发逻辑.
 */
class MvcController {
    private lateinit var model: MvcModel
    private lateinit var view: MvcView

    fun initialize(tv: TextView) {
        // 连接 model 和 view
        model = MvcModel()
        view = MvcView(tv)
        view.setupModel(model)

        // 设置外部事件并调用 model 响应
        view.setTvClickListener {
            model.randomNumToString()
        }
    }
}

/**
 * 特点:
 *  1. Model 负责所有逻辑, 既包含了业务逻辑又包含了 UI 逻辑.
 *  2. Model 通过观察者向外部通知变更.(也是因为其包含了 UI 逻辑)
 */
class MvcModel {
    private var data: String = "1234"
    private var dataUpdateListener: ((newValue: String?) -> Unit)? = null

    fun setDataChangeListener(listener: (newValue: String?) -> Unit) {
        this.dataUpdateListener = listener
    }

    fun randomNumToString() {
        // 核心业务逻辑(domain logic)
        val newNum = Random.nextInt()

        // UI 逻辑(UI logic)
        data = if (newNum <= 999) {
            newNum.toString()
        } else {
            "999+"
        }

        // model -> view
        notifyChange()
    }

    private fun notifyChange() {
        dataUpdateListener?.invoke(data)
    }
}

/**
 * 特点:
 *  直接引用 Model, 并订阅其数据的变更.
 */
class MvcView(
    val tv: TextView,
) {
    fun setupModel(model: MvcModel) {
        model.setDataChangeListener {
            updateResult(it)
        }
    }

    fun setTvClickListener(listener: View.OnClickListener) {
        tv.setOnClickListener(listener)
    }

    /******* UI 展示 *******/
    private fun updateResult(text: String?) {
        // 实现 ui 绘制逻辑
        tv.text = text
    }
}