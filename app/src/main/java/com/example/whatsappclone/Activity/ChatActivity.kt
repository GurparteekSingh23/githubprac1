package com.example.whatsappclone.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.OutcomeReceiver
import android.widget.Toast
import com.example.whatsappclone.Adapter.MessageAdapter
import com.example.whatsappclone.Model.MessageModel
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var senderUid:String
    private lateinit var receiverUid:String
    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String

    private lateinit var list:ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        senderUid=FirebaseAuth.getInstance().uid.toString()

        receiverUid=intent.getStringExtra("uid")!!

        senderRoom=senderUid+receiverUid
        list=ArrayList()
        receiverRoom=receiverUid+senderUid
        database= FirebaseDatabase.getInstance()
        binding.imageView3.setOnClickListener {
            if(binding.messageBox.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your message",Toast.LENGTH_SHORT).show()
            }
            else{
                val message=MessageModel(binding.messageBox.text.toString(),senderUid, Date().time)

                val randomKey=database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!)
                    .setValue(message).addOnSuccessListener {


                        database.reference.child("chats").child(receiverRoom) .child ("message")
                            .child(randomKey!!).setValue(message).addOnSuccessListener {
                                binding.messageBox.text=null
                                Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }
        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
            list.clear()
                    for(snapshot1 in snapshot.children){
                        val data=snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }
                    binding.chatRecyclerView.adapter=MessageAdapter(this@ChatActivity,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity, "Error:$error", Toast.LENGTH_SHORT).show()

                }
            } )
    }
}