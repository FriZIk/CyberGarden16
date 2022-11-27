package com.crafsed.sas.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.crafsed.sas.R
import com.crafsed.sas.databinding.LoginFragmentBinding
import com.crafsed.sas.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment() : Fragment(R.layout.login_fragment) {
    private var _binding: LoginFragmentBinding? = null
    private val loginViewModel: LoginViewModel by sharedViewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loading = true

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it == true) {
                (requireActivity() as LoginActivity).toMainFragment()
            } else if (it == false){
                loginError()
            }
        }

        binding.passwordLayout.setOnFocusChangeListener { v, hasFocus ->
            binding.passwordLayout.error = null
        }

        binding.loginButton.setOnClickListener {
            val transition: Transition = Fade()

            transition.setDuration(500)
            transition.addTarget(binding.loginLayout)
            transition.addTarget(binding.passwordLayout)
            transition.addTarget(binding.loginButton)
            transition.addTarget(binding.microsoftButton)
            transition.addTarget(binding.progressBar)

            TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
            binding.loginLayout.visibility = View.INVISIBLE
            binding.passwordLayout.visibility = View.INVISIBLE
            binding.loginButton.visibility = View.INVISIBLE
            binding.microsoftButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE

            loginViewModel.login(
                binding.loginField.text.toString(),
                binding.passwordField.text.toString()
            )
        }
    }

    private fun loginError() {
        binding.passwordLayout.error = "Не удалось выполнить вход"

        val transition: Transition = Fade()

        transition.setDuration(500)
        transition.addTarget(binding.loginLayout)
        transition.addTarget(binding.passwordLayout)
        transition.addTarget(binding.loginButton)
        transition.addTarget(binding.microsoftButton)
        transition.addTarget(binding.progressBar)

        TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
        binding.loginLayout.visibility = View.VISIBLE
        binding.passwordLayout.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
        binding.microsoftButton.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}