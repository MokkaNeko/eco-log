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
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.SessionManager
import com.mokaneko.recycle2.databinding.FragmentRegisterBinding
import com.mokaneko.recycle2.ui.MainActivity
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateRegisterInputCard()

        binding.btnRegister.setOnClickListener{
            btnRegisterFunction()
        }

        binding.registerTvToLogin.setOnClickListener {
            registerToLogin()
        }

        viewModel.authState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    // User berhasil register & otomatis login
                    val user = result.data
                    sessionManager.saveUserSession(user.uid)
                    // Arahkan ke MainActivity
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    // tampilkan error
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun btnRegisterFunction() {
        val strEmail: String = binding.registerEmailEditText.text.toString()
        val strPassword: String = binding.registerPasswordEditText.text.toString()
        val strRetypePassword: String = binding.registerRetypePasswordEditText.text.toString()

        if ((strEmail.isNotEmpty())&&(strPassword.isNotEmpty())&&(strRetypePassword.isNotEmpty())){
            if (strPassword == strRetypePassword) {
                viewModel.register(strEmail, strPassword)
            } else {
                Toast.makeText(activity, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Harap masukkan email dan password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerToLogin() {
        val fragmentLogin = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.welcomeFragmentView, fragmentLogin)
            .addToBackStack(null)
            .commit()
    }

    private fun animateRegisterInputCard() {
        val fadeSlide = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_slide_up)
        val fadeOnly = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.registerLlInputLayout.alpha = 0f
        binding.registerCvInputLayout.startAnimation(fadeSlide)

        fadeSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.registerLlInputLayout.startAnimation(fadeOnly)
                binding.registerLlInputLayout.alpha = 1f
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}