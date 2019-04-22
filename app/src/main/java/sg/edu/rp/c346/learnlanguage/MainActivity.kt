package sg.edu.rp.c346.learnlanguage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser



class MainActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase:FirebaseDatabase
    private lateinit var databaseReference:DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        btnTrainee.setOnClickListener {
            postTrain("trainee")
        }
        btnTrainer.setOnClickListener{
            postTrain("trainer")
        }

        signIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    fun postTrain(role: String){
        val email:String = etEmail.text.toString()
        val pass:String = etPass.text.toString()
        register(email, pass, role)

    }

    fun register(email:String, pass:String, role:String){
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    var train = User(email, pass, role)
                    if (user != null) {
                        databaseReference.child(user.uid).setValue(train)
                    }
                    Toast.makeText(
                        baseContext, "Registration Success.",
                        Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        baseContext, "Registration Failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
