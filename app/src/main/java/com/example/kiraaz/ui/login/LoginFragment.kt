package com.example.kiraaz.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        /*if (viewModel.mAuth.currentUser != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            findNavController().navigate(R.id.action_global_searchFragment)
        }*/

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleLogin()
        loginButton()
        signupButton()


        return binding.root
    }

    private fun loginButton() {
        binding.loginBtn.setOnClickListener {
            if (binding.emailTv.text.isNullOrEmpty()) {
                binding.email.error = "Email is required"
                return@setOnClickListener
            }
            binding.email.error = null
            if (binding.passwordTv.text.isNullOrEmpty()) {
                binding.password.error = "Password is required"
                return@setOnClickListener
            }
            binding.password.error = null
            viewModel.login(binding.emailTv.text.toString(), binding.passwordTv.text.toString())
            viewModel.isLoginSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_global_searchFragment)
                } else {
                    Toast.makeText(requireContext(), viewModel.errorLogin.value, Toast.LENGTH_SHORT)
                        .show()
                    binding.email.error = viewModel.errorLogin.value
                }
            }
        }
    }

    private fun signupButton(){
        binding.signupBtn.setOnClickListener{
            if (binding.emailTv.text.isNullOrEmpty()) {
                binding.email.error = "Email is required"
                return@setOnClickListener
            }
            binding.email.error = null
            if (binding.passwordTv.text.isNullOrEmpty()) {
                binding.password.error = "Password is required"
                return@setOnClickListener
            }
            binding.password.error = null
            viewModel.register(binding.emailTv.text.toString(), binding.passwordTv.text.toString())
            viewModel.isRegisterSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(requireContext(), "Register Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_profilingFragment)
                } else {
                    Toast.makeText(requireContext(), viewModel.errorRegister.value, Toast.LENGTH_SHORT)
                        .show()
                    binding.email.error = viewModel.errorRegister.value
                }
            }
        }
    }

    private fun googleLogin() {
        binding.googleBtn.setOnClickListener {
            signIn()
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.signInWithGoogle(account)
                viewModel.isGoogleSignInSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
                    if (isSuccessful) {
                        /*viewModel.isNewUser.observe(viewLifecycleOwner) {
                            if (it) {
                                val action = LoginFragmentDirections.actionLoginFragmentToProfileInfoFragment(true)
                                findNavController().navigate(action)
                            } else {
                                findNavController().navigate(R.id.action_global_searchFragment)
                            }
                        }*/
                    } else {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}