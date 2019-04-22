package sg.edu.rp.c346.learnlanguage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_schedule.*
import java.text.SimpleDateFormat
import java.util.*

class Schedule : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        auth = FirebaseAuth.getInstance()
        var name:String=""
        var targetName:String=""

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        var username:String = intent.getStringExtra("username")
        var skype:String = intent.getStringExtra("skype")
        var line:String = intent.getStringExtra("line")
        var wechat:String = intent.getStringExtra("wechat")

        tvUsername1.setText(username)
        tvSkype.setText(skype)
        tvLine.setText(line)
        tvWe.setText(wechat)

        var uid:String = intent.getStringExtra("uid")
        var currentUser = auth.currentUser
        var trainee:DatabaseReference
        var trainer:DatabaseReference
        if(currentUser!=null) {
            var trainee = databaseReference.child(currentUser.uid)
            var trainer = databaseReference.child(uid)

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (datas in dataSnapshot.children) {
                        if(trainee.key == datas.key){
                            name = datas.child("username").value.toString()
                            for (datas1 in dataSnapshot.children) {
                                if(trainer.key == datas1.key){
                                    targetName = datas1.child("username").value.toString()
                                }
                            }
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }

            }
            databaseReference.addValueEventListener(postListener)
        }
        datePicker.updateDate(2019, 0, 9)
        timePicker.hour = 0
        timePicker.minute = 0

        btnBack.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {

            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val hour = timePicker.hour
            val min = timePicker.minute
            val newDate = Calendar.getInstance()
            newDate.set(year, month, day, hour, min)
            val sdf = SimpleDateFormat("dd/MMM/YYYY")
            val stf = SimpleDateFormat("hh:mm a")
            val date = sdf.format(newDate.time)
            val time = stf.format(newDate.time)

            var requestTrainee1 = ArrayList<String>()
            var requestTrainer1 = ArrayList<String>()
            if(currentUser!=null) {
                var trainee = databaseReference.child(currentUser.uid)
                var trainer = databaseReference.child(uid)
                requestTrainee1.add("Scheduled study session with " + targetName + " on " + date + " at " + time)
                trainee.child("request").setValue(requestTrainee1)
                requestTrainer1.add("Request to study with " + name + " on " + date + " at " + time)
                trainer.child("request").setValue(requestTrainer1)

                Toast.makeText(baseContext, "Sent! Please be punctual & check your messages.",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {
    }
}
