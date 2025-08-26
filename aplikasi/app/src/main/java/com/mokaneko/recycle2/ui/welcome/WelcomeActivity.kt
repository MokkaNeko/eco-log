package com.mokaneko.recycle2.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.SessionManager
import com.mokaneko.recycle2.databinding.ActivityWelcomeBinding
import com.mokaneko.recycle2.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = sessionManager.getUserSession()
        if (userId != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.post {
            animateWelcomeViews()
        }

        binding.welcomeBtnLogin.setOnClickListener{
            replaceFragment(LoginFragment())
        }
        binding.welcomeBtnRegister.setOnClickListener{
            replaceFragment(RegisterFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.welcomeFragmentView, fragment)
        fragmentTransaction.commit()
    }

    private fun animateWelcomeViews() {
        binding.welcomeTitle.alpha = 0f
        binding.welcomeText.alpha = 0f
        binding.welcomeBtnLogin.alpha = 0f
        binding.welcomeBtnRegister.alpha = 0f
        binding.welcomeSlogan.alpha = 0f

        val duration = 500L
        var delay = 0L

        binding.welcomeTitle.animate()
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .start()

        delay += 300L
        binding.welcomeText.animate()
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .start()

        delay += 300L
        binding.welcomeBtnLogin.animate()
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .start()

        binding.welcomeBtnRegister.animate()
            .alpha(1f)
            .setStartDelay(delay + 100L)
            .setDuration(duration)
            .start()

        delay += 300L
        binding.welcomeSlogan.animate()
            .alpha(1f)
            .setStartDelay(delay + 200L)
            .setDuration(duration)
            .start()
    }
}