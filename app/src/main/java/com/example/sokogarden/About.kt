package com.example.sokogarden

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class About : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize TextToSpeech
        tts = TextToSpeech(this, this)

        val storyText = findViewById<TextView>(R.id.about_text_story)
        val missionText = findViewById<TextView>(R.id.about_text_mission)
        val fabTts = findViewById<FloatingActionButton>(R.id.fab_tts)

        fabTts.setOnClickListener {
            if (isTtsReady) {
                val fullText = "Our Story: ${storyText.text} Our Mission: ${missionText.text}"
                tts?.speak(fullText, TextToSpeech.QUEUE_FLUSH, null, "AboutID")
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isTtsReady = true
            }
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}