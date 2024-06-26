package com.bintangfajarianto.submission1.ui.authentication.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bintangfajarianto.submission1.R
import com.bintangfajarianto.submission1.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel by activityViewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show Loading
        registerViewModel.isLoading.observe(viewLifecycleOwner) {
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
            btnRegister.setOnClickListener {
                if (nameField.text.toString().isNotBlank()
                    && emailField.text.toString().isNotBlank()
                    && passwordField.text.toString().isNotBlank()
                    && emailField.error == null
                    && passwordField.error == null) {

                    registerViewModel.register(
                        nameField.text.toString().trim(),
                        emailField.text.toString().trim(),
                        passwordField.text.toString()
                    )

                    // Handle Error Responses
                    registerViewModel.message.observe(viewLifecycleOwner) { event ->
                        event.getContentIfNotHandled()?.let { text ->
                            Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    // Handle Success Responses
                    registerViewModel.responseRegister.observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), R.string.register_valid, Toast.LENGTH_SHORT).show()

                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().safeNavigate(action)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.register_invalid,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            goToLogin.setOnClickListener (
                Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            nameField.isEnabled = !isLoading
            emailField.isEnabled = !isLoading
            passwordField.isEnabled = !isLoading
            btnRegister.isEnabled = !isLoading
        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }
}