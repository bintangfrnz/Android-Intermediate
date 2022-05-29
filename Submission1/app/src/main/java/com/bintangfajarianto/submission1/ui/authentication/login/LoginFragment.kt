package com.bintangfajarianto.submission1.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.databinding.LoginFragmentBinding
import com.bintangfajarianto.submission1.di.ViewModelFactory
import com.bintangfajarianto.submission1.ui.home.HomeActivity
import com.bintangfajarianto.submission1.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show Loading
        loginViewModel.isLoading.observe(viewLifecycleOwner) {
            setLoading(it)
        }

        // Set Up Action
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

                        Intent(requireContext(), HomeActivity::class.java).also { intent ->
                            intent.putExtra(EXTRA_TOKEN, it.loginResult.token)
                            startActivity(intent)
                            requireActivity().finish()
                        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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