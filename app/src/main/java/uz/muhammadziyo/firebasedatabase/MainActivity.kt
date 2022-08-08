package uz.muhammadziyo.firebasedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import uz.muhammadziyo.firebasedatabase.adapters.RvAdapter
import uz.muhammadziyo.firebasedatabase.databinding.ActivityMainBinding
import uz.muhammadziyo.firebasedatabase.models.MyUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseFirestore: FirebaseFirestore


    private val rvAdapter by lazy { RvAdapter() }
    private val list = ArrayList<MyUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.rv.adapter = rvAdapter

        binding.apply {
            firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        result.forEach {
                            val user = it.toObject(MyUser::class.java)
                            list.add(user)
                        }
                    }
                    rvAdapter.submitList(list)
                }
            btnSave.setOnClickListener {
                val user = MyUser(name.text.toString(), edtAge.text.toString())
                firebaseFirestore.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Toast.makeText(this@MainActivity, "Save", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }

            }
        }

        onRvClick()

    }

    fun onRvClick() {
        rvAdapter.itemClick = {
            Toast.makeText(this, "${it.name}", Toast.LENGTH_SHORT).show()
        }
    }
}