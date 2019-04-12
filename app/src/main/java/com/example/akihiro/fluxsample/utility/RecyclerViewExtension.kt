package com.example.akihiro.fluxsample.utility

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * RecyclerViewが引数で指定したポジションまでスクロールしたらcallback関数を実行する
 * @param limit
 * @param callBack
 */
fun RecyclerView.pageNationListener(limit: Int, callBack: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            (recyclerView.layoutManager as? LinearLayoutManager)
                ?.findFirstVisibleItemPosition()
                ?.takeIf { firstPosition ->
                    limit == recyclerView.childCount + firstPosition
                }
                ?.let { callBack.invoke() }
        }
    })
}