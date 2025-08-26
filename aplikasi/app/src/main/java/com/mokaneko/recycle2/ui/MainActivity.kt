package com.mokaneko.recycle2.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.ActivityMainBinding
import com.mokaneko.recycle2.ui.kamera.CameraActivity
import com.mokaneko.recycle2.ui.katalog.KatalogFragment
import com.mokaneko.recycle2.ui.kategori.KategoriFragment
import com.mokaneko.recycle2.ui.terbuang.TerbuangFragment
import com.mokaneko.recycle2.ui.welcome.AuthViewModel
import com.mokaneko.recycle2.ui.welcome.WelcomeActivity
import com.mokaneko.recycle2.utils.Resource
import com.mokaneko.recycle2.utils.SearchableFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        Inflate Layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topMenu.root.alpha = 0f
        binding.mainFragmentContainer.alpha = 0f
        binding.menuAdd.alpha = 0f
        binding.bottomNavMenu.root.alpha = 0f

        binding.topMenu.root.animate()
            .alpha(1f)
            .setDuration(800)
            .start()
        binding.bottomNavMenu.root.animate()
            .alpha(1f)
            .setDuration(800)
            .start()
        binding.menuAdd.animate()
            .alpha(1f)
            .setDuration(800)
            .withEndAction {
                binding.mainFragmentContainer.animate()
                    .alpha(1f)
                    .setDuration(600)
                    .start()
            }
            .start()


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        setCurrentFragment(KatalogFragment(), "Katalog Sampah")
        binding.bottomNavMenu.menuItemCatalog.alpha = 1f
        binding.bottomNavMenu.menuImageKatalog.setColorFilter(ContextCompat.getColor(this, R.color.green_primary))
        binding.bottomNavMenu.menuTextKatalog.setTextColor(ContextCompat.getColor(this, R.color.green_primary))

//        Bottom Menu Handler
        mainViewModel.activeFragmentTag.observe(this) { tag ->
            resetMenuColors()
            binding.topMenu.topMenuTitle.text = tag
            binding.topMenu.searchLayout.visibility = View.VISIBLE
            binding.topMenu.searchLayout.layoutParams.height = MATCH_PARENT
            binding.topMenu.searchLayout.requestLayout()
            when (tag) {
                "Katalog Sampah" -> {
                    binding.bottomNavMenu.menuItemCatalog.alpha = 1f
                    binding.bottomNavMenu.menuImageKatalog.setColorFilter(ContextCompat.getColor(this, R.color.green_primary))
                    binding.bottomNavMenu.menuTextKatalog.setTextColor(ContextCompat.getColor(this, R.color.green_primary))
                }
                "Sampah Terbuang" -> {
                    binding.bottomNavMenu.menuItemSampah.alpha = 1f
                    binding.bottomNavMenu.menuImageTerbuang.setColorFilter(ContextCompat.getColor(this, R.color.green_primary))
                    binding.bottomNavMenu.menuTextTerbuang.setTextColor(ContextCompat.getColor(this, R.color.green_primary))
                }
                "Kategori" -> {
                    binding.bottomNavMenu.menuItemCategory.alpha = 1f
                    binding.bottomNavMenu.menuImageKategori.setColorFilter(ContextCompat.getColor(this, R.color.green_primary))
                    binding.bottomNavMenu.menuTextKategori.setTextColor(ContextCompat.getColor(this, R.color.green_primary))
                    binding.topMenu.searchLayout.visibility = View.INVISIBLE
                    binding.topMenu.searchLayout.layoutParams.height = 0
                    binding.topMenu.searchLayout.requestLayout()
                }
            }
        }

//        Logout Handler
        authViewModel.logoutState.observe(this) { result ->
            when (result) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Gagal logout: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

//        Button Listener
        binding.bottomNavMenu.menuItemCatalog.setOnClickListener {
            setCurrentFragment(KatalogFragment(), "Katalog Sampah")
        }
        binding.bottomNavMenu.menuItemSampah.setOnClickListener {
            setCurrentFragment(TerbuangFragment(), "Sampah Terbuang")
        }
        binding.bottomNavMenu.menuItemCategory.setOnClickListener {
            setCurrentFragment(KategoriFragment(), "Kategori")
        }
        binding.bottomNavMenu.menuItemLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ -> authViewModel.logout() }
                .setNegativeButton("Batal", null)
                .show()
        }
        binding.menuAdd.setOnClickListener {
            openCameraIfPermissionGranted()
        }
        binding.topMenu.searchEditText.addTextChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
            if (currentFragment is SearchableFragment) {
                currentFragment.onSearchQueryChanged(it.toString())
            }
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
            fragment?.tag?.let { tag -> mainViewModel.setActiveFragment(tag) }
        }
    }

//    Fungsi Berpindah Fragment
    private fun setCurrentFragment(fragment: Fragment, title: String, tag: String = title) {
        val current = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (current?.javaClass == fragment.javaClass) return

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, fragment, tag)
            addToBackStack(tag)
            commit()
        }
        mainViewModel.setActiveFragment(tag)
    }

//    Fungsi reset state menu
    private fun resetMenuColors() {
        val black = ContextCompat.getColor(this, R.color.black)
        binding.bottomNavMenu.menuItemCatalog.alpha = 0.3f
        binding.bottomNavMenu.menuImageKatalog.setColorFilter(black)
        binding.bottomNavMenu.menuTextKatalog.setTextColor(black)
        binding.bottomNavMenu.menuItemSampah.alpha = 0.3f
        binding.bottomNavMenu.menuImageTerbuang.setColorFilter(black)
        binding.bottomNavMenu.menuTextTerbuang.setTextColor(black)
        binding.bottomNavMenu.menuItemCategory.alpha = 0.3f
        binding.bottomNavMenu.menuImageKategori.setColorFilter(black)
        binding.bottomNavMenu.menuTextKategori.setTextColor(black)
        binding.bottomNavMenu.menuItemLogout.alpha = 0.3f
        binding.bottomNavMenu.menuImageKeluar.setColorFilter(black)
        binding.bottomNavMenu.menuTextKeluar.setTextColor(black)
    }

//    Fungsi Kamera dan Ijin
    private fun openCameraIfPermissionGranted() {
        if (allPermissionsGranted()) {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCameraIfPermissionGranted()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}