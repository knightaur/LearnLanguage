package sg.edu.rp.c346.learnlanguage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.FirebaseError
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    var hashMap = HashMap<String, String>()

    override fun onBackPressed() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datas in dataSnapshot.children) {
                    hashMap.put(datas.key.toString(), datas.child("role").getValue().toString())
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        databaseReference.addValueEventListener(postListener)

        backRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            login(email, pass)
        }
    }

    fun login(email:String, pass:String){
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    if (user != null) {
                        if(checkRole(user) == "trainer"){
                            intent = Intent(this, HomeTrainer::class.java)
                            Toast.makeText(baseContext, "Welcome Trainer",
                                Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                        else {
                            var intent = Intent(this, Home::class.java)
                            Toast.makeText(baseContext, "Welcome Trainee",
                                Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
                } else {
                    Toast.makeText(baseContext, "Login Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun checkRole(user:FirebaseUser):String{
        for (i in hashMap.entries){
            Log.i("EntriesTest1", "" + i.key)
            Log.i("EntriesTest2", "" + i.value)
            if(user.uid.equals(i.key)){
                return i.value
            }
        }
        return ""
    }

}
