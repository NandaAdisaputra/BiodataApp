package com.qiswatululfah.biodataapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.qiswatululfah.biodataapp.R
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun showPopup(v: View?) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.popup_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.itemView -> {
                startActivity<MainActivity>()
                true
            }
            R.id.itemBuat -> {
                startActivity(intentFor<CreateBiodataActivity>("FLAG" to "ADD"))
                true
            }
            else -> false
        }
    }
}
