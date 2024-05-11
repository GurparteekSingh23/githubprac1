package com.example.whatsappclone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.whatsappclone.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val tabTitle= arrayOf("Chats","Calls","Status")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var pager=findViewById<ViewPager2>(R.id.viewPager2)
        var tl=findViewById<TabLayout>(R.id.tablayout)
        pager.adapter=MyAdapter(supportFragmentManager,lifecycle)


        TabLayoutMediator(tl,pager){
            tab,position->
            tab.text=tabTitle[position]
        }.attach()
        auth= FirebaseAuth.getInstance()

        if(auth.currentUser==null){

            startActivity(Intent(this,NumberActivity::class.java))
            finish()
        }


    }
}