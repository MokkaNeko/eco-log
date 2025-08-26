package com.mokaneko.recycle2.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.FragmentForgetPasswordBinding
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateForgetInputCard()

        binding.forgotBtnKirim.setOnClickListener {
            val email = binding.forgotEmailEditText.text.toString().trim()
            if (email.isEmpty()) {
                binding.forgotEmailInputLayout.error = "Email tidak boleh kosong"
            } else {
                binding.forgotEmailInputLayout.error = null
                authViewModel.sendPasswordReset(email)
            }
        }

        authViewModel.resetPasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, "Link reset telah dikirim", Snackbar.LENGTH_LONG)
                        .setAction("Kembali ke Login") {
                            forgotToLogin()
                        }
                        .show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.forgotTvForgotToRegister.setOnClickListener {
            forgotToRegister()
        }

    }

    private fun forgotToRegister() {
        val fragmentRegister = RegisterFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcomeFragmentView, fragmentRegister)
            .addToBackStack(null)
            .commit()
    }

    private fun forgotToLogin() {
        val fragmentLogin = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcomeFragmentView, fragmentLogin)
            .addToBackStack(null)
            .commit()

    }

    private fun animateForgetInputCard() {
        val fadeSlide = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_slide_up)
        val fadeOnly = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.forgotLlInputLayout.alpha = 0f
        binding.forgotCvInputLayout.startAnimation(fadeSlide)

        fadeSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.forgotLlInputLayout.startAnimation(fadeOnly)
                binding.forgotLlInputLayout.alpha = 1f
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}