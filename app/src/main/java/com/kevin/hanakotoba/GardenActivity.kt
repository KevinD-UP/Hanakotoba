package com.kevin.hanakotoba

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.kevin.hanakotoba.databinding.ActivityGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    private val binding : ActivityGardenBinding by lazy{ ActivityGardenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if(intent.extras?.getBoolean("FromNotif") != null ){
            if(intent.extras?.getBoolean("FromNotif") == true){
                val myFlowersFragment = MyFlowersFragment(true)
                this.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,myFlowersFragment)
                    .addToBackStack(null).commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.openFlowersToWater ->  {
                val myFlowersFragment = MyFlowersFragment(true)
                this.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,myFlowersFragment)
                    .addToBackStack(null).commit()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
