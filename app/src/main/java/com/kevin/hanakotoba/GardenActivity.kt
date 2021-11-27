package com.kevin.hanakotoba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.hanakotoba.databinding.ActivityGardenBinding

class GardenActivity : AppCompatActivity() {

    val binding : ActivityGardenBinding by lazy{ ActivityGardenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // SETUP DATABASE WITH https://sqlitebrowser.org/dl/
        // Export file as .db , copy into assets/database/




    }

}
