package com.example.texttospeechkotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.ImageView
import java.util.*
@SuppressLint("ClickableViewAccessibility")
class SecondActivity : AppCompatActivity() {
    lateinit var editText:EditText
    lateinit var imageView:ImageView
    lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        bindview()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val speechintent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechintent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                editText.setText("")
                editText.hint = "Listening..."
            }

            override fun onBeginningOfSpeech() {

            }
            override fun onRmsChanged(rmsdB: Float) {

            }
            override fun onBufferReceived(buffer: ByteArray) {

            }
            override fun onEndOfSpeech() {

            }
            override fun onError(error: Int) {

            }
            override fun onResults(results: Bundle) {
                imageView.setImageResource(R.drawable.mic)
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val datatext = data!![0]
                editText.hint = data!![0]
            }

            override fun onPartialResults(partialResults: Bundle) {}
            override fun onEvent(eventType: Int, params: Bundle) {}
        })

        imageView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening()
            }
            if (event.action == MotionEvent.ACTION_DOWN) {
                imageView.setImageResource(R.drawable.mic2)
                speechRecognizer.startListening(speechintent)
            }
            false
        }
    }
    private fun bindview() {
        editText = findViewById(R.id.convert_to_speectedit)
        imageView = findViewById(R.id.mic_btn)
    }
}