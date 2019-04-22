package sg.edu.rp.c346.learnlanguage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var aaReqs: AnotherAdapter
    var alReqs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")


        auth = FirebaseAuth.getInstance()
        var current = auth.currentUser
        if(current!=null){
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        if(datas.key.toString() == current.uid){
                            if(datas.child("request").value != null){
                                for (i in (datas.child("request").value as ArrayList<String>)){
                                    alReqs.add(i)
                                    Log.i("TestRun1", "" + i)
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


        for (i in alReqs){
            Log.i("TestRun", "" + i)
        }
        aaReqs = AnotherAdapter(this, R.layout.request_item, alReqs)
        lvReqs.adapter = aaReqs
    }
}
