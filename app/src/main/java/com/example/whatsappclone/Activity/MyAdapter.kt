package com.example.whatsappclone.Activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whatsappclone.fragment.CallFragment
import com.example.whatsappclone.fragment.MessageFragment
import com.example.whatsappclone.fragment.StatusFragment

class MyAdapter(fragmentManager:FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       when(position){
           0->return MessageFragment()
           1->return CallFragment()
           2->return  StatusFragment()
           else->return MessageFragment()

       }
    }
}