package com.example.khent.spotify.Fragment


import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.khent.spotify.MainActivity
import com.example.khent.spotify.R
import com.example.khent.spotify.model.SongList
import kotlinx.android.synthetic.main.fragmentsong.*
import kotlinx.android.synthetic.main.fragmentsong.view.*
import java.io.IOException


class SongFragment : Fragment(), View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private lateinit var SongPlay: TextView
    private lateinit var SingerPlay: TextView
    private lateinit var BtnPlay: ImageView
    private var mMediaPlayer: MediaPlayer? = null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragmentsong, container, false)
        val song = arguments!!.getParcelable(MainActivity.KEY) as SongList

        setViews(rootView)
        mMediaPlayer = MediaPlayer()
        try {
            if (mMediaPlayer!!.isPlaying)
                mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource(song.mSongPath)
            mMediaPlayer?.prepare()
        } catch (
                e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (
                e: IllegalStateException
        ) {
            e.printStackTrace()
        } catch (
                e: IOException
        ) {
            e.printStackTrace()
        }

        SongPlay.text = song.Name
        SingerPlay.text = song.Singer
        BtnPlay.setOnClickListener(this)
        mMediaPlayer?.setOnPreparedListener(this)
        mMediaPlayer?.setOnCompletionListener(this)

        return rootView
    }

    override fun onClick(view: View?) {
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer?.pause()
            setPlayImage(view)
        } else {
            mMediaPlayer?.seekTo(mMediaPlayer!!.currentPosition)
            mMediaPlayer?.start()
            setPauseImage(view)
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mMediaPlayer!!.start()
        setPauseImage(btnPausePlay)
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        setPlayImage(btnPausePlay)
    }

    private fun setPlayImage(view: View?) {
        view?.setBackgroundResource(R.drawable.playbtn)
    }

    private fun setPauseImage(view: View?) {
        view?.setBackgroundResource(R.drawable.pause)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    private fun setViews(view: View) {
        SongPlay = view.txtSong
        SingerPlay = view.txtAlbum
        BtnPlay = view.btnPausePlay
    }


}
