package com.example.pogodynka

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.xmlpull.v1.XmlPullParser
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import androidx.core.view.*
import androidx.lifecycle.ViewModelProvider
import com.example.pogodynka.repository.Repository
import com.example.pogodynka.viewModel.UserViewModel
import com.example.pogodynka.viewModel.UserViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val repository = Repository()
        val viewModelFactory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val temperatureText = findViewById<TextView>(R.id.temperature_text)
        val sunriseText = findViewById<TextView>(R.id.sunrise_text)
        val sunsetText = findViewById<TextView>(R.id.sunset_text)
        val airPressureText = findViewById<TextView>(R.id.air_pressure_text)
        val humidityText = findViewById<TextView>(R.id.humidity_text)
        val cityText = findViewById<TextView>(R.id.city_text)
        val weatherIcon = findViewById<ImageView>(R.id.weather_icon)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val sunriseIcon = findViewById<ImageView>(R.id.sunrise_icon)
        val sunsetIcon = findViewById<ImageView>(R.id.sunset_icon)

        return when (item.itemId) {

            R.id.action_senior -> {
                if (!item.isChecked) {
                    viewModel.seniorMode = true
                    item.isChecked = true
                    item.setIcon(R.drawable.senior)

                    temperatureText.textSize = 50f
                    temperatureText.setPadding(70, 20,50,30)
                    weatherIcon.scaleX = 1.1f
                    weatherIcon.scaleY = 1.1f
                    cityText.textSize = 40f
                    airPressureText.textSize = 32f
                    humidityText.textSize = 32f
                    sunriseText.textSize = 32f
                    sunriseText.setPadding(0,20,130,0)
                    sunriseIcon.scaleX = 0.9f
                    sunriseIcon.scaleY = 0.9f
                    sunsetIcon.scaleX = 0.9f
                    sunsetIcon.scaleY = 0.9f
                    sunsetText.textSize = 32f
                    sunsetText.setPadding(0,20,130,0)
                    fab.scaleX = 1.3f
                    fab.scaleY = 1.3f
                    fab.setPadding(0,0,20,100)

                } else {
                    viewModel.seniorMode = false
                    item.isChecked = false
                    item.setIcon(R.drawable.not_senior)
                    temperatureText.textSize = 38f
                    temperatureText.setPadding(100,100,70,100)
                    weatherIcon.scaleX = 1f
                    weatherIcon.scaleY = 1f
                    cityText.textSize = 36f
                    airPressureText.textSize = 20f
                    humidityText.textSize = 20f
                    sunriseText.textSize = 22f
                    sunriseText.setPadding(0,40,90,0)
                    sunriseIcon.scaleX = 0.6f
                    sunriseIcon.scaleY = 0.6f
                    sunsetIcon.scaleX = 0.6f
                    sunsetIcon.scaleY = 0.6f
                    sunsetText.textSize = 22f
                    sunsetText.setPadding(0,40,90,0)
                    fab.scaleX = 1f
                    fab.scaleY = 1f
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}