package com.example.pogodynka

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogodynka.repository.Repository
import com.example.pogodynka.viewModel.UserViewModel
import com.example.pogodynka.viewModel.UserViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.Timestamp
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    var location = "Gliwice"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val repository = Repository()
        val viewModelFactory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.getWeather(location)

                // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_default, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempetatureText = view.findViewById<TextView>(R.id.temperature_text)
        val sunriseText = view.findViewById<TextView>(R.id.sunrise_text)
        val sunsetText = view.findViewById<TextView>(R.id.sunset_text)
        val airPressureText = view.findViewById<TextView>(R.id.air_pressure_text)
        val humidityText = view.findViewById<TextView>(R.id.humidity_text)
        val cityText = view.findViewById<TextView>(R.id.city_text)
        val timeText = view.findViewById<TextView>(R.id.time_text)
        val weatherIcon = view.findViewById<ImageView>(R.id.weather_icon)

        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()!!.name)
                Log.d("Response", response.body()!!.clouds.all.toString())
                Log.d("Response", response.body()!!.dt.toString())

                sunriseText.text = formatTime(response.body()!!.sys.sunrise)
                sunsetText.text = formatTime(response.body()!!.sys.sunset)
                cityText.text = response.body()!!.weather[0].description.capitalize()
                tempetatureText.text = response.body()!!.main.temp.toString() + "°C"
                airPressureText.text = "Air pressure: " + response.body()!!.main.pressure.toString()
                humidityText.text = "Humidity: " + response.body()!!.main.humidity.toString()
                timeText.text = SimpleDateFormat("dd.M.yyyy hh:mm:ss").format(Date())
                //requireActivity().actionBar!!.title = response.body()!!.name
                var toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
                toolbar.setTitle(response.body()!!.name)

                when (response.body()!!.weather[0].description) {
                    "clear sky" -> {
                        weatherIcon.setImageResource(R.drawable.sun)
                    }
                    "rain" -> {
                        weatherIcon.setImageResource(R.drawable.rain)
                    }
                    "snow" -> {
                        weatherIcon.setImageResource(R.drawable.snow)
                    }
                    "broken clouds" -> {
                        weatherIcon.setImageResource(R.drawable.broken_clouds)
                    }
                    "overcast clouds"->{
                        weatherIcon.setImageResource(R.drawable.overcast_clouds)
                    }
                    "thunderstorm" -> {
                        weatherIcon.setImageResource(R.drawable.thunder)
                    }
                    "wind" -> {
                        weatherIcon.setImageResource(R.drawable.wind)
                    }
                    "drizzle" -> {
                        weatherIcon.setImageResource(R.drawable.drizzle)
                    }
                    "light rain"->{
                        weatherIcon.setImageResource(R.drawable.drizzle)
                    }
                    "mist" -> {
                        weatherIcon.setImageResource(R.drawable.mist)
                    }
                    "scattered clouds"->{
                        weatherIcon.setImageResource(R.drawable.scattered_clouds)
                    }
                    "few clouds"->{
                        weatherIcon.setImageResource(R.drawable.few_clouds)
                    }
                    else -> {

                    }
                }
            } else {
                var toast = Toast.makeText(context, "We couldn't find that city!\nMake sure the spelling is right.", Toast.LENGTH_LONG)
                val view = toast.view
                view!!.background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                val text = view!!.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                if(viewModel.seniorMode) text.textSize = 30f
                toast.show()
            }
        })

        var fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->

            this.let {
                var editText = EditText(requireContext())
                editText.hint = "e.g. Gliwice"
                editText.setPadding(30)
                val builder = AlertDialog.Builder(requireContext())

                val inflater = this.layoutInflater

                builder.setView(editText)
                        .setTitle("Search for a city:")

                        .setPositiveButton(R.string.ok,
                                DialogInterface.OnClickListener { dialog, id ->
                                    location = editText.text.toString()
                                    changeCity(view)
                                })

                        .setNegativeButton(R.string.cancel,
                                DialogInterface.OnClickListener { dialog, id ->

                                })
                builder.create()
                builder.show()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    fun changeCity(view: View){
        Log.d("Lokacja", location)
        val repository = Repository()
        val viewModelFactory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.getWeather(location)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(unix: Long): String{
        val sdf = java.text.SimpleDateFormat("HH:mm")
        val date = java.util.Date(unix * 1000)
        return sdf.format(date)
    }
}