package com.qiswatululfah.biodataapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qiswatululfah.biodataapp.R
import com.qiswatululfah.biodataapp.algoritma.RSA
import com.qiswatululfah.biodataapp.room.BiodataDatabase
import com.qiswatululfah.biodataapp.room.BiodataModel
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class UpdateBiodataActivity : AppCompatActivity() {

    var biodataDatabase: BiodataDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        biodataDatabase = BiodataDatabase.getInstance(this)

        val biodata = intent.getParcelableExtra<BiodataModel?>("BIODATA")
        edt_number.setText(intent.getStringExtra("number"))
        edt_name.setText(intent.getStringExtra("name"))
        edt_gender.setText(intent.getStringExtra("gender"))
        edt_address.setText(intent.getStringExtra("address"))
        edt_birth.setText(intent.getStringExtra("birth"))

        btn_update.onClick {
            startActivity<MainActivity>()
            updateBiodata(biodata?.id)
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

    private fun updateBiodata(id: Long?) {
        val cipher = doEncrypt()
        GlobalScope.launch {
            biodataDatabase?.biodataDao()?.updateBiodata(
                edt_number.text.toString(),
                cipher.toString(),
                edt_birth.text.toString(),
                edt_gender.text.toString(),
                edt_address.text.toString(),
                id!!
            )
            runOnUiThread {
                toast("Berhasil Update Biodata")
                finishAffinity()
                startActivity(intentFor<MainActivity>())
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
