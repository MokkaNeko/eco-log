package com.mokaneko.recycle2.ui.welcome

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import com.mokaneko.recycle2.ui.MainActivity
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.SessionManager
import com.mokaneko.recycle2.databinding.FragmentLoginBinding
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateLoginInputCard()

        binding.loginBtnLogin.setOnClickListener{
            loginBtnFunction()
        }
        binding.loginTvToRegister.setOnClickListener {
            loginToRegister()
        }
        binding.loginTvToForgot.setOnClickListener {
            loginToForgot()
        }
        viewModel.authState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    val user = result.data
                    binding.progressBar.isVisible = false
                    sessionManager.saveUserSession(user.uid)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loginBtnFunction() {
        val email = binding.loginEmailEditText.text.toString().trim()
        val password = binding.loginPasswordEditText.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()){
            viewModel.login(email, password)
        }else {
            Toast.makeText(activity, "Harap masukkan email dan password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginToRegister() {
        val fragmentRegister = RegisterFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcomeFragmentView, fragmentRegister)
            .addToBackStack(null)
            .commit()
    }

    private fun loginToForgot() {
        val fragmentForgot = ForgetPasswordFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcomeFragmentView, fragmentForgot)
            .addToBackStack(null)
            .commit()

    }

    private fun animateLoginInputCard() {
        val fadeSlide = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_slide_up)
        val fadeOnly = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.loginLlInputLayout.alpha = 0f
        binding.loginCvInputLayout.startAnimation(fadeSlide)

        fadeSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.loginLlInputLayout.startAnimation(fadeOnly)
                binding.loginLlInputLayout.alpha = 1f
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

}