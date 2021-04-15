package com.example.labtest1.feeskeeper.serviceondrive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private var  currentId  : String = ""
    override fun onReceive(context: Context, intent: Intent) {




       val geo  :  GeofencingEvent = GeofencingEvent.fromIntent(intent)
        val db = Firebase.firestore

        val geofences = geo.triggeringGeofences


        geofences.map {


            currentId  = it.requestId

        }


        println(currentId)



        val transType = geo.geofenceTransition

        if(transType == Geofence.GEOFENCE_TRANSITION_ENTER ){

            println("hey hey hey Enteteddddddd")

            val docRef = db.collection("ridedetails").document("ride").collection("driverDetails")
                .document("details")

            docRef.update("rideStatus" , currentId )

        }else if (  transType == Geofence.GEOFENCE_TRANSITION_EXIT  ){


            println("hey hey hey Exit")

        }else if ( transType == Geofence.GEOFENCE_TRANSITION_DWELL ){

            println("hey hey hey GEOFENCE_TRANSITION_DWELL")
        }


    }


}