package com.example.khent.spotify

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
import com.example.khent.spotify.Fragment.SongFragment
import com.example.khent.spotify.adapters.SongListAdapter
import com.example.khent.spotify.model.SongList
import com.example.khent.spotify.service.PlayMusic
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by khent on 2/3/2018.
 */
class MainActivity : AppCompatActivity() {

    internal lateinit var recyclerView: RecyclerView
    var songModelData: ArrayList<SongList> = ArrayList()
    var songListAdapter: SongListAdapter? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()

    companion object {
        val PERMISSION_REQUEST_CODE = 1
        const val KEY = "asdad"
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       findview()


        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
        }else{
            loadData()
        }
    }


    private fun loadData() {
        val songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null)

        while (songCursor != null && songCursor.moveToNext()) {
            val songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val songAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            val songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

            songModelData.add(SongList(songName, songArtist, songAlbum,songPath))
        }

        songListAdapter = SongListAdapter(mSelected,songModelData)
        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        recyclerView1.itemAnimator = DefaultItemAnimator()
        recyclerView1.adapter = songListAdapter

        recyclerView1.addOnItemTouchListener(PlayMusic(this, object : PlayMusic.ClickListener {


            override fun onClick(view: View, position: Int) {
                val fragmentContainer = fragmentSong
                val musicFragment = SongFragment()
                val bundle = Bundle()

                bundle.putParcelable(KEY, songModelData[position])
                musicFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragmentSong,musicFragment).commit()
                view.isSelected = true
                fragmentContainer.visibility =  View.VISIBLE

            }
        }))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Permission Granted", Toast.LENGTH_SHORT).show()
                loadData()
            }
        }

        }
    fun findview(){
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
    }
}