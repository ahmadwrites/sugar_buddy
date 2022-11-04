package com.example.assignment_pe3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var imgLogo: ImageView
    private lateinit var tvLogoTitle: TextView
    private lateinit var tvLogoSubTitle: TextView

    private lateinit var logoAnimation: Animation
    private lateinit var titleAnimation: Animation
    private lateinit var subtitleAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        imgLogo = findViewById<ImageView>(R.id.img_logo);
        tvLogoTitle = findViewById<TextView>(R.id.tv_logo_title);
        tvLogoSubTitle = findViewById<TextView>(R.id.tv_logo_subtitle);

        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_animation)
        subtitleAnimation = AnimationUtils.loadAnimation(this, R.anim.subtitle_animation)

        imgLogo.animation = logoAnimation
        tvLogoTitle.animation = titleAnimation
        tvLogoSubTitle.animation = subtitleAnimation

        Handler(Looper.getMainLooper()).postDelayed({
            val intent: Intent = Intent(this, GetStartedActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}