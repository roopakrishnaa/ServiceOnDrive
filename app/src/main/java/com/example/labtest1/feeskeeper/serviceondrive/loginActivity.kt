package com.example.labtest1.feeskeeper.serviceondrive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.DriverDetails
import com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel
import kotlinx.android.synthetic.main.activity_login.*


lateinit var createbtn : TextView
lateinit var login :Button


private lateinit var driverDetailsViewModel: driverDetailsViewModel
private lateinit var  currentDrivers :  List<DriverDetails>
private lateinit var  currentDriver :  DriverDetails


class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var mail : EditText
         var  pass :EditText
        createbtn = findViewById(R.id.CreateAcc)
        login = findViewById(R.id.loginbtn)

        mail = findViewById(R.id.mail)
        pass =  findViewById(R.id.password)

        createbtn.setOnClickListener {
            var intent = Intent(this , SignupActivity::class.java)

            startActivity(intent)

        }


        driverDetailsViewModel = ViewModelProvider(this).get(com.example.labtest1.feeskeeper.serviceondrive.DbConfig.driverDetailsViewModel::class.java)

        driverDetailsViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {


                currentDrivers = it

                println("helllllllooooworrllldd")
                println("Size "+ currentDrivers.size)

            }

        })



        login.setOnClickListener {


            var emailtxt = mail.text.toString()
            var  passtxt = password.text.toString()

            this.login(emailtxt , passtxt )


        }



    }


    private fun login(email:String, password:String){


        if( email== "" ) {

            Toast.makeText(this,"please enter email" , Toast.LENGTH_SHORT).show()
            return

        }else if (password == ""){
            Toast.makeText(this,"please enter password" , Toast.LENGTH_SHORT).show()
            return
        }else

        {

            currentDrivers.map {
                if ( it.Email.equals(email)  && it.Password.equals(password) ){

                   currentDriver  = it


                    finish()

                    val goto = Intent(this , MainActivity::class.java)
                    goto.putExtra("driverDetails" , currentDriver )
                    startActivity(goto)


                    Toast.makeText(this,"Login in ..." , Toast.LENGTH_SHORT).show()


                }else {


                    Toast.makeText(this,"User does not exist , please Sign up!!" , Toast.LENGTH_SHORT).show()


                }


            }

        }



    }






}