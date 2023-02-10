package leo.skvorc.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import leo.skvorc.nasa.databinding.ActivityRegisterBinding
import leo.skvorc.nasa.framework.startActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConfirmation = binding.etPasswordConfirmation.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()) {
                if (password == passwordConfirmation) {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) startActivity<LogInActivity>()
                        else Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                else Toast.makeText(this, R.string.passwords_must_match, Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(this, R.string.fill_all_fealds, Toast.LENGTH_LONG).show()
        }
    }
}