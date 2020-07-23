package com.qiswatululfah.biodataapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qiswatululfah.biodataapp.R
import com.qiswatululfah.biodataapp.algoritma.RSA
import com.qiswatululfah.biodataapp.room.BiodataDatabase
import com.qiswatululfah.biodataapp.room.BiodataModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class DetailBiodataActivity : AppCompatActivity() {

    var biodataDatabase: BiodataDatabase? = null

    var isDecrypt = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        biodataDatabase = BiodataDatabase.getInstance(this)

        //Mendapatkan data yang dibawa saat berpindah ke halaman ini
        val biodata = intent.getParcelableExtra<BiodataModel?>("BIODATA")

        //Menampilkan data pada tampilan yang telah disediakan
        tv_number.text = biodata?.number.toString()
        tv_name.text = biodata?.name
        tv_birth.text = biodata?.birth
        tv_gender.text = biodata?.gender
        tv_address.text = biodata?.address

        btn_dekrip.onClick {
            decryptMessage(biodata)
        }

        iv_delete.onClick {
            deleteNote(biodata)
        }

        iv_edit.setOnClickListener {
            val intent = Intent(this, UpdateBiodataActivity::class.java)
            intent.putExtra("FLAG", "UPDATE")
            intent.putExtra("BIODATA", biodata)
            intent.putExtra("number", tv_number.text.toString())
            intent.putExtra("name", tv_name.text.toString())
            intent.putExtra("birth", tv_birth.text.toString())
            intent.putExtra("gender", tv_gender.text.toString())
            intent.putExtra("address", tv_address.text.toString())
            startActivity(intent)


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

    private fun decryptMessage(biodata: BiodataModel?) {
        if (!isDecrypt) {
            val RSA = RSA()
            val keyEncrypt = RSA.eValue(RSA.Qn)
            val keyDecrypt = RSA.dValue(RSA.Qn, keyEncrypt)

            var plainTeks = ""
            for (element in biodata?.name!!) {
                val character = element
                val plain = RSA.decrypt(character, keyDecrypt, RSA.n)
                plainTeks += plain
            }
            tv_name.text = plainTeks
            isDecrypt = true
            btn_dekrip.text = "Enkripsi"
        } else {
            tv_name.text = biodata?.name
            isDecrypt = false
            btn_dekrip.text = "Dekripsi"
        }
    }

    private fun deleteNote(biodata: BiodataModel?) {
        GlobalScope.launch {
            biodataDatabase?.biodataDao()?.deleteBiodata(biodata!!)
            runOnUiThread {
                toast("Berhasil hapus biodata")
                this@DetailBiodataActivity.finish()
            }
        }
    }

}
