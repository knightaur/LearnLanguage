package sg.edu.rp.c346.learnlanguage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var caTrainer: CustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.setTitle("Trainee")

        auth = FirebaseAuth.getInstance()

        var alTrainer = ArrayList<User>()
        var targetTrainerUid = ArrayList<String>()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datas in dataSnapshot.children) {

                    if(datas.child("role").value.toString() == "trainer"){
                        alTrainer.add(User
                            (   datas.child("email").value.toString(),
                                datas.child("pass").value.toString(),
                                datas.child("role").value.toString(),
                                datas.child("username").value.toString(),
                                datas.child("skype").value.toString(),
                                datas.child("wechat").value.toString(),
                                datas.child("line").value.toString() ))
                        targetTrainerUid.add(datas.key.toString())
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        databaseReference.addValueEventListener(postListener)

        btnProfile.setOnClickListener {
            var intent = Intent(this, profile::class.java)
            intent.putExtra("role", "trainee")
            startActivity(intent)
        }



        caTrainer = CustomAdapter(this, R.layout.trainer_item, alTrainer)
        lvTrainers.adapter = caTrainer

        btnSearch.setOnClickListener {
            var alTrainers = ArrayList<User>()
            for(i in alTrainer){
                if(i.username.contains(etName.text.toString(), true)){
                        alTrainers.add(i)
                }
            }
            caTrainer = CustomAdapter(this, R.layout.trainer_item, alTrainers)
            lvTrainers.adapter = caTrainer
        }


        lvTrainers.setOnItemClickListener{
                parent: AdapterView<*>?, view: View?, position:Int, id:Long ->
            var intent = Intent(this, Schedule::class.java)
            var currentTrainer:User = alTrainer.get(position)
            var targetUID:String = targetTrainerUid.get(position)
            intent.putExtra("uid", targetUID)
            intent.putExtra("username", currentTrainer.username)
            intent.putExtra("skype", currentTrainer.skype)
            intent.putExtra("line", currentTrainer.line)
            intent.putExtra("wechat", currentTrainer.wechat)
            startActivity(intent)
        }

        btnNotification.setOnClickListener{
            var intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }




    }

    override fun onBackPressed() {
    }

}
