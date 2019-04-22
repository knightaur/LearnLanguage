package sg.edu.rp.c346.learnlanguage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*

class profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        if(user != null){
            if(tvUsername.text.toString().equals("username") || tvEmail.text.toString().equals("email")){
                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase.getReference("users")

                val postListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (datas in dataSnapshot.children) {
                            if(user.uid == datas.key){
                                tvUsername.setText(datas.child("username").value.toString())
                                tvEmail.text = datas.child("email").value.toString()
                                if(!(datas.child("skype").value.toString() == "null") || !(datas.child("line").value.toString() == "null") ||
                                    !(datas.child("wechat").value.toString() == "null")){
                                    etSkypeID.setText(datas.child("skype").value.toString())
                                    etLine.setText(datas.child("line").value.toString())
                                    etWechat.setText(datas.child("wechat").value.toString())
                                }
                            }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                }
                databaseReference.addValueEventListener(postListener)
            }
        }

        btnUpdate.setOnClickListener {
            if(user != null){
                var target = databaseReference.child(user.uid)
                target.child("username").setValue(tvUsername.text.toString())
                target.child("skype").setValue(etSkypeID.text.toString())
                target.child("line").setValue(etLine.text.toString())
                target.child("wechat").setValue(etWechat.text.toString())
            }

        }

        btnHome.setOnClickListener {
            finish()
        }


        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Logged Out",
                Toast.LENGTH_SHORT).show()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        if(!(intent.getStringExtra("role") == "trainee")){
            btnNotification2.visibility = View.GONE
            btnHome.setText("Requests")
        }

        btnNotification2.setOnClickListener{
            var intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
    }
}
