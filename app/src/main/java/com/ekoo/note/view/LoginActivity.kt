package com.ekoo.note.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ekoo.note.NoteViewModel
import com.ekoo.note.R
import com.ekoo.note.obtainViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = obtainViewModel(this)
        viewModel.signInStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                startActivity<HomeActivity>()
                finish()
            } else {
                toast("ERROR")
            }
        })

        signIn_button.setOnClickListener {
            startActivityForResult(
                viewModel.signInIntent,
                SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            viewModel.signIn(data)
        }
    }

    companion object {
        private const val SIGN_IN = 11
    }
}
