package com.qiswatululfah.biodataapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiswatululfah.biodataapp.R
import com.qiswatululfah.biodataapp.adapter.BiodataAdapter
import com.qiswatululfah.biodataapp.room.BiodataDatabase
import com.qiswatululfah.biodataapp.room.BiodataModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class MainActivity : AppCompatActivity() {

    var biodataDatabase: BiodataDatabase? = null

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: BiodataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biodataDatabase = BiodataDatabase.getInstance(this)
        layoutManager = LinearLayoutManager(this)
        adapter = BiodataAdapter(ArrayList())
        rv_biodata.layoutManager = layoutManager
        rv_biodata.adapter = adapter

        getAllBiodata()

        val path = getDatabasePath("Biodata.db").canonicalPath
        Log.d("DATABASE", "Path Database $path")


        ma_fab.onClick {
            startActivity(intentFor<CreateBiodataActivity>("FLAG" to "ADD"))
        }
        btn_back.onClick {
                alert("Apakah anda ingin kembali halaman sebelumnya ?") {
                    noButton {
                        toast("Anda tidak jadi kembali ke halaman sebelumnya")
                        finish()
                    }
                    yesButton {
                        onBackPressed()
                    }
                }.show()
            }
        }

    private fun getAllBiodata() {
        GlobalScope.launch {
            val list: List<BiodataModel>? = biodataDatabase?.biodataDao()?.getAllBiodata()
            runOnUiThread {
                adapter.setListOfBiodata(list)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllBiodata()
    }

}