package com.hcs.findmedev.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.hcs.core.R
import com.hcs.findmedev.MainActivity
import com.hcs.findmedev.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val notificationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startMainAfterDelay()
            } else {
                // Kalau user menolak, tetap stuck di splash atau kasih pesan
                showPermissionRequiredDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.textView2) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemBars.bottom)
            insets
        }

        binding.lottieImg.speed = 1f

        requestNotificationPermissionIfNeeded()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionRequest.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                startMainAfterDelay()
            }
        } else {
            startMainAfterDelay()
        }
    }

    private fun startMainAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMain()
        }, 4000)
    }

    private fun showPermissionRequiredDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required))
            .setMessage(getString(R.string.aplikasi_membutuhkan_izin_notifikasi_agar_fitur_berjalan_dengan_baik_silakan_izinkan))
            .setCancelable(false) // Tidak bisa di-cancel
            .setPositiveButton(getString(R.string.izinkan)) { _, _ ->
                // Request lagi
                notificationPermissionRequest.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(getString(R.string.keluar)) { _, _ ->
                finish()
            }
            .show()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
