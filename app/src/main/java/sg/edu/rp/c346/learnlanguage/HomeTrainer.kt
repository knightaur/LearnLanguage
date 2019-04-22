package sg.edu.rp.c346.learnlanguage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_trainer.*

class HomeTrainer : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var aaReq: AnotherAdapter
    var alReq = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_trainer)
        this.setTitle("Trainer")

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        btnProfile.setOnClickListener {
            var intent = Intent(this, profile::class.java)
            intent.putExtra("role", "trainer")
            startActivity(intent)
        }

        var currentUser = auth.currentUser
        if(currentUser!=null){
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        if(datas.key.toString() == currentUser.uid){
                            if(datas.child("request").value != null){
                                for (i in (datas.child("request").value as ArrayList<String>)){
                                    alReq.add(i)
                                    Log.i("TestRun1", "" + i)
                                    doWork()
                                }
                                Log.i("TestRun0", "" + datas.child("request").value)

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

    fun doWork(){
        Log.i("TestRun3", "" + alReq)
        aaReq = AnotherAdapter(this, R.layout.request_item, alReq)
        lvReq1.adapter = aaReq
    }

    override fun onBackPressed() {
    }
}
