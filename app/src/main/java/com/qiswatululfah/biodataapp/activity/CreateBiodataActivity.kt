package com.qiswatululfah.biodataapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qiswatululfah.biodataapp.R
import com.qiswatululfah.biodataapp.algoritma.RSA
import com.qiswatululfah.biodataapp.room.BiodataDatabase
import com.qiswatululfah.biodataapp.room.BiodataModel
import kotlinx.android.synthetic.main.activity_create_biodata.*
import kotlinx.android.synthetic.main.activity_create_biodata.btn_back
import kotlinx.android.synthetic.main.activity_update.edt_address
import kotlinx.android.synthetic.main.activity_update.edt_birth
import kotlinx.android.synthetic.main.activity_update.edt_gender
import kotlinx.android.synthetic.main.activity_update.edt_name
import kotlinx.android.synthetic.main.activity_update.edt_number
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CreateBiodataActivity : AppCompatActivity() {

    var biodataDatabase: BiodataDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_biodata)
        biodataDatabase = BiodataDatabase.getInstance(this)
        val flag = intent.getStringExtra("FLAG")

        btn_create.onClick {
            if (flag == "ADD") {
                insertNewBiodata()
            } else {
                toast("failed to make biodata")
            }
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

    private fun insertNewBiodata() {
        val cipher = doEncrypt()
        val biodata = BiodataModel(
            number = edt_number.text.toString(),
            name = cipher,
            birth = edt_birth.text.toString(),
            gender = edt_gender.text.toString(),
            address = edt_address.text.toString()
        )

        GlobalScope.launch {
            biodataDatabase?.biodataDao()?.insertBiodata(biodata)
            runOnUiThread {
                startActivity<MainActivity>()
                toast("Data berhasil ditambahkan")
            }
        }
    }

    private fun doEncrypt(): String? {
        val RSA = RSA()
        val keyEncrypt = RSA.eValue(RSA.Qn)
        Log.d("ENCRYPT", "E VALUE $keyEncrypt")
        val name = edt_name.text.toString()
        var cipherTeks = ""
        for (element in name) {
            val cipher = RSA.encrypt(element, keyEncrypt, RSA.n)
            cipherTeks += cipher
        }
        Log.d("Encryption", cipherTeks)
        return cipherTeks
    }


}
