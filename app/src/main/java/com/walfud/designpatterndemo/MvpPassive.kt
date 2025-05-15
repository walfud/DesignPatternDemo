package com.walfud.designpatterndemo

import android.view.View
import android.widget.TextView
import kotlin.random.Random

/**
 * 彻底解耦 modal 和 view:
 *  1. 承担 model 和 view 之间复杂的逻辑调度(如 `handleXxx` 中).
 *  2. 承担 UI 逻辑(如 `toDescString` 中).
 */
class MvpPassivePresenter(
    private val model: MvpPassiveModel,
) {
    private lateinit var view: MvpPassiveView
    fun attachView(view: MvpPassiveView) {
        this.view = view
    }

    /**
     * 在 Presenter 中调用了 model 执行业务逻辑, 并在自身进行 ui 拼装, 最终调度给 view 层.
     */
    fun handleXxx() {
        val num = model.genRandomNum()
        val descStr = toDescString(num)
        view.updateResult(descStr)
    }

    /********* UI 逻辑(UI logic) *********/
    fun toDescString(num: Int): String? {
        return if (num <= 999) {
            num.toString()
        } else {
            "999+"
        }
    }
}

/**
 * 特点:
 *  纯业务逻辑, 不涉及任何 UI 逻辑
 */
class MvpPassiveModel {
    /********* 业务逻辑 (domain logic) *********/
    fun genRandomNum(): Int {
        return Random.nextInt()
    }
}

/**
 * 特点:
 *  1. 责进行 ui 展示.
 *  2. 负责将 ui 事件转发给 Presenter.
 */
class MvpPassiveView(
    val tv: TextView,
    private val presenter: MvpPassivePresenter,
) {
    init {

        tv.setOnClickListener {
            presenter.handleXxx()
        }
    }

    /******* UI 展示 *******/
    fun updateResult(text: String?) {
        // 实现 ui 绘制逻辑
        tv.text = text
    }
}