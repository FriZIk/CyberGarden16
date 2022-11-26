package com.crafsed.sas.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.crafsed.sas.R
import com.crafsed.sas.databinding.LoginFragmentBinding

class LoginFragment() : Fragment(R.layout.login_fragment) {
    private var _binding: LoginFragmentBinding? = null

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

        binding.loginButton.setOnClickListener {
            val transition: Transition = Fade()

            transition.setDuration(500)
            transition.addTarget(binding.loginLayout)
            transition.addTarget(binding.passwordLayout)
            transition.addTarget(binding.loginButton)
            transition.addTarget(binding.microsoftButton)
            transition.addTarget(binding.progressBar)

            TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
            binding.loginLayout.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
            binding.passwordLayout.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
            binding.loginButton.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
            binding.microsoftButton.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
            binding.progressBar.visibility = (if (loading) View.VISIBLE else View.GONE)

            (requireActivity() as LoginActivity).toMainFragment()
        }
    }

}