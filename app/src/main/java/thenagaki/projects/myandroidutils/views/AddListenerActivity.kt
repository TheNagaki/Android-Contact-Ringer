package thenagaki.projects.myandroidutils.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import thenagaki.projects.myandroidutils.R
import thenagaki.projects.myandroidutils.databinding.ActivityAddListenerBinding

class AddListenerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddListenerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddListenerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelListenerBtn.setOnClickListener {
            finish()
        }
    }
}