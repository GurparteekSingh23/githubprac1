package com.example.whatsappclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.whatsappclone.Adapter.ChatAdapter
import com.example.whatsappclone.Model.UserModel
import com.example.whatsappclone.R

import com.example.whatsappclone.databinding.FragmentMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageFragment : Fragment() {
    private var database:FirebaseDatabase?=null
    private lateinit var userList: ArrayList<UserModel>

private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentMessageBinding.inflate(layoutInflater)
        database=FirebaseDatabase.getInstance()
        userList= ArrayList()
        database!!.reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   userList.clear()
                    for(snapshot1 in snapshot.children){
                        val user=snapshot1.getValue(UserModel::class.java)
                        if(user!!.uid!=FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }
                    binding.userListRecycle.adapter=ChatAdapter(requireContext(),userList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        return binding.root
}







    }


