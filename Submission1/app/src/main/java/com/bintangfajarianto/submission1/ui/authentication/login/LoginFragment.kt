package com.bintangfajarianto.submission1.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.databinding.FragmentLoginBinding
import com.bintangfajarianto.submission1.di.ViewModelFactory
import com.bintangfajarianto.submission1.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        // Show Loading
        loginViewModel.isLoading.observe(viewLifecycleOwner) {
            setLoading(it)
        }

        setUpAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (emailField.text.toString().isNotBlank()
                    && passwordField.text.toString().isNotBlank()
                    && emailField.error == null
                    && passwordField.error == null) {

                    loginViewModel.login(
                        emailField.text.toString().trim(),
                        passwordField.text.toString()
                    )

                    // Handle Error Responses
                    loginViewModel.message.observe(viewLifecycleOwner) { event ->
                        event.getContentIfNotHandled()?.let { text ->
                            Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    // Handle Success Responses
                    loginViewModel.responseLogin.observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), R.string.login_valid, Toast.LENGTH_SHORT).show()

                        val intentHome = Intent(requireContext(), HomeActivity::class.java)
                        intentHome.putExtra(HomeActivity.EXTRA_TOKEN, it.loginResult.token)
                        startActivity(intentHome)
                        requireActivity().finish()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.login_invalid,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            goToRegister.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            emailField.isEnabled = !isLoading
            passwordField.isEnabled = !isLoading
            btnLogin.isEnabled = !isLoading
        }
    }
}