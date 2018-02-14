package com.example.khent.spotify.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by khent on 2/3/2018.
 */
class PlayMusic(context: Context, private val clickListener: ClickListener) : RecyclerView.OnItemTouchListener {
    private val mContext = context

    private val detector = GestureDetector (mContext, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean = true
    })

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv!!.findChildViewUnder(e!!.x, e.y)

        if (child != null && detector.onTouchEvent(e))
            clickListener.onClick(child, rv.getChildAdapterPosition(child))

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    interface ClickListener {
        fun onClick (view: View, position: Int)

    }
}