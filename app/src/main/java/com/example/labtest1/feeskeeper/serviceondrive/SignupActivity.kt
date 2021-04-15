package com.example.labtest1.feeskeeper.serviceondrive

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.DriverDetails
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel
import java.io.ByteArrayOutputStream

lateinit var loginbtn :TextView
lateinit var firstname :EditText
lateinit var lastname :EditText


lateinit var imageView: ImageView
lateinit var SetImageBtn :Button
var imgData = ""


private lateinit var DriverDetailsViewModel: driverDetailsViewModel
private lateinit var  currentUsers :  List<DriverDetails>
private lateinit var  currentUser :  DriverDetails



class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)

        DriverDetailsViewModel = ViewModelProvider(this).get(com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel::class.java)


        DriverDetailsViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {


                currentUsers = it

                println("helllllllooooworrllldd")
                println("Size "+ currentUsers.size)

            }

        })



        var email :EditText

         var password :EditText
         var RegisterBtn : Button

        loginbtn = findViewById(R.id.login)
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        RegisterBtn = findViewById(R.id.Registerbtn)
        imageView  = findViewById(R.id.Dispic)
        SetImageBtn = findViewById(R.id.SetImg)


        loginbtn.setOnClickListener {
            finish()

        }


        RegisterBtn.setOnClickListener {


            val firstname = firstname.text.toString()
            val lastname = lastname.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()

            println("helloworld")

            save(firstname, lastname, password, email, imgData)

        }

        SetImageBtn.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent  , 1)

        }


    }



    private fun save(
        firstname: String,
        lastname: String,
        password: String,
        email: String,
        imgData: String
    ) {

        if (firstname == "") {

            Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()


            return
        } else if (lastname == "") {

            Toast.makeText(this, "Enter last name", Toast.LENGTH_LONG).show()


            return

        } else if (password == "") {

            Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()


            return

        } else if (email == "") {

            Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()


            return

        } else if (imgData == "") {

            Toast.makeText(this, " please select image", Toast.LENGTH_LONG).show()


            return

        } else {


            var result = validEmail(email)
            var  exist = ifUserExist(email)

            if (result) {
                if ( !exist){

                    val userDetails = DriverDetails(
                        0,
                        firstname,
                        lastname,
                        email,
                        password,
                        imgData,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        "",
                        "",
                        "",
                        false
                    )
                    DriverDetailsViewModel.insert(userDetails)
                    Toast.makeText(this, "YAYA! You are finally registered ..!!", Toast.LENGTH_LONG)
                        .show()
                    finish()

                }
                else{

                    Toast.makeText(this , "User already Exists!!" ,Toast.LENGTH_LONG).show()

                }

            }

            else{

                Toast.makeText(this , "Not a well formed email" ,Toast.LENGTH_LONG).show()
            }

        }

    }



    //Starting camera activity on result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {



            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)


            val byteArrayOutputStream =
                ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()


            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

            imgData = encoded

        }
    }


    private fun   validEmail( email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    private fun ifUserExist(email: String) : Boolean {

        var exits = false
        currentUsers.map {

            if(it.Email.equals(email) ) {

                exits = true
            }
        }
        return  exits
    }


}

