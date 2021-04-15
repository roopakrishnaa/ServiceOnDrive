package com.example.labtest1.feeskeeper.serviceondrive

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.DriverDetails
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.Observer



class MainActivity : AppCompatActivity() , LocationListener {


    private lateinit var title :TextView
    private lateinit var fname :TextView
    private lateinit var lname :TextView
    private lateinit var destination :TextView
    private lateinit var currentloction :TextView
    private lateinit var Accept :Button
    private lateinit var DriverDetailsViewModel: driverDetailsViewModel
    private lateinit var  currentDrivers :  List<DriverDetails>
    private lateinit var cu : DriverDetails
    private lateinit var locationManager: LocationManager


    var riderLatitude : Double  = 0.0
    var riderLongitude :Double = 0.0
    var destinationLatitue :Double =  0.0
    var destinationLongitude :Double =  0.0
    private val locationPermissionCode = 2
    var currentlati :Double = 0.0
    var currentlongi : Double = 0.0

//dummy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        getLocation()

        cu = intent.getParcelableExtra("driverDetails")!!



        DriverDetailsViewModel = ViewModelProvider(this).get(com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel::class.java)


        DriverDetailsViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {



                currentDrivers = it

                println("helllllllooooworrllldd")
                println("Size "+ currentDrivers.size)



                currentDrivers.map {



                    if(cu.DriverId == it.DriverId ){

                        title.text = "welcome    " + it.FirstName
                        cu = it


                    }



                    Accept.setOnClickListener {


                        getLocation()
                        val db = Firebase.firestore
                        db.collection("ridedetails").document("ride").collection("driverDetails").document("details" ).set(cu)
                        val togo = Intent(this , customerEnrouteMap::class.java)
                        startActivity(togo)

                    }

                }


            }

        })


        var riderImg = findViewById<ImageView>(R.id.riderimg)

        title = findViewById(R.id.title)
        fname = findViewById(R.id.firstName)
        lname  = findViewById(R.id.lastName)
        destination  = findViewById(R.id.Destination)
        currentloction = findViewById(R.id.currentlocation)
        Accept = findViewById(R.id.accept)



        val db = Firebase.firestore

        val docRef = db.collection("ridedetails").document("ride")


         db.collection("ridedetails").document("ride")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {


                return@addSnapshotListener

            }

            if (snapshot != null && snapshot.exists()) {

               // println(snapshot.exists())
                println("Current data: ${snapshot.get("firstName") }")

               // title.text = "Riders Found "
                fname.text = snapshot.get("firstName").toString()
                lname.text = snapshot.get("lastNmae").toString()
                destination.text = "Destination "  + snapshot.get("formattedDestination").toString()
                currentloction.text = "Rider's current location "  + snapshot.get("formattedCurrentLocation").toString()
                 val abc= snapshot.get("currentLatititue").toString()
                riderLatitude = abc.toDouble()
                val xyz  = snapshot.get("currentLongitude").toString()
                riderLongitude =  xyz.toDouble()


                val destilat= snapshot.get("destinationLatititue").toString()
                destinationLatitue = destilat.toDouble()
                val destilong  = snapshot.get("destinationLongitude").toString()
                destinationLongitude = destilong.toDouble()

                val imgData = snapshot.get("userImg").toString()
                val k =  Base64.decode(imgData, Base64.DEFAULT)
                val image = BitmapFactory.decodeByteArray(k, 0, k.size)
                riderImg.setImageBitmap( image)

                val cd = DriverDetails(cu.DriverId , cu.FirstName , cu.LastNmae , cu.Email , cu.Password  , cu.UserImg , cu.CurrentLatititue , cu.currentLongitude ,riderLatitude,riderLongitude,  destinationLatitue, destinationLongitude , cu.formattedDestination , cu.FormattedCurrentLocation,"",false)

                DriverDetailsViewModel.update(cd)


            } else {

            }
        }


    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {

       currentlati = location.latitude
       currentlongi = location.longitude

        val cd = DriverDetails(cu.DriverId , cu.FirstName , cu.LastNmae , cu.Email , cu.Password  , cu.UserImg , currentlati, currentlongi ,cu.RidersLatititue,cu.RidersLongitude,  cu.DestinationLatititue, cu.DestinationLongitude , cu.formattedDestination , cu.FormattedCurrentLocation,"",false)

        DriverDetailsViewModel.update(cd)


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }




}
