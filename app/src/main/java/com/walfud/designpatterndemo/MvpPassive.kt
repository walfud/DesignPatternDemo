package com.walfud.designpatterndemo

import android.view.View
import android.widget.TextView
import kotlin.random.Random

/**
 * 为了解耦 modal 和 view:
 *  1. 连接 View 和 Model.
 *  2. 注册外部事件后调用 Model 触发逻辑.
 *  3. 承担 model 和 view 之间复杂的逻辑调度(如 `handleXxx` 中).
 */
class MvpPassivePresenter(
    private val view: MvpPassiveView,
    private val model: MvpPassiveModel,
) {
    fun initialize(tv: TextView) {
        // 设置外部事件并调用 model 响应
        view.setTvClickListener {
            handleXxx()
        }
    }

    /**
     * 在 Presenter 中调用了 model 执行业务逻辑, 并在自身进行 ui 拼装, 最终调度给 view 层.
     */
    private fun handleXxx() {
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
 *  纯视图(真哑视图), 仅进行 ui 展示.
 */
class MvpPassiveView(
    val tv: TextView,
) {
    fun setTvClickListener(listener: View.OnClickListener) {
        tv.setOnClickListener(listener)
    }

    /******* UI 展示 *******/
    fun updateResult(text: String?) {
        // 实现 ui 绘制逻辑
        tv.text = text
    }
}