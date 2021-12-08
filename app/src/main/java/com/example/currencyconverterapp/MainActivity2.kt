package com.example.currencyconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.currencyconverter.APIInterface
import com.example.currencyconverterapp.R
import kotlinx.coroutines.*
///import okhttp3.Call
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback


import org.json.JSONObject
import java.net.URL
///import javax.security.auth.callback.Callback

class MainActivity2 : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var result: TextView
    lateinit var editText: EditText
    lateinit var buttonConvert: Button
    lateinit var date :TextView
    var selected = 0
    var selectedCur = 0.0
    // private var Currencyy:Currency? = null
    var listVersionAndroid: MutableList<String> = ArrayList()
    var listVersionAndroid2: MutableList<Double> = ArrayList()
    var arraydata = arrayListOf<String>()
    var arrayValue = arrayListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        result = findViewById(R.id.result)
        editText = findViewById(R.id.editText)
        buttonConvert = findViewById(R.id.buttonCon)
        date = findViewById(R.id.date)

        buttonConvert.setOnClickListener {
            var userInput = editText.text.toString().toDouble()
            result.setText((selectedCur*userInput).toString())

        }
        loadJSON()




    }

    fun spinnerSetup() {
        requestAPI()

        var currencySelect = ""

        if (spinner != null) {
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, arraydata
            )
            spinner.adapter = adapter

        }

    }

    /* private fun getCurrency(onResult: (Currency_Item?) -> Unit) {
         val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

         if (apiInterface != null) {
             apiInterface.getCurrency()?.enqueue(object : Callback<Currency_Item> {
                 override fun onResponse(
                     call: Call<Currency_Item>,
                     response: Response<Currency_Item>
                 ) {
                     onResult(response.body())

                 }

                 override fun onFailure(call: Call<Currency>, t: Throwable) {
                     onResult(null)
                     Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show();
                 }

             })
         }*/

    private fun requestAPI(){

        CoroutineScope(Dispatchers.IO).launch {
            // we fetch the data
            val data = async { fetchData() }.await()

            if(data.isNotEmpty()){
                getAdvice(data)
            }else{
                Log.d("MAIN", "Unable to get data")
            }
        }
    }

    private fun fetchData(): String{

        var response = ""
        try{
            response = URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json").readText()
        }catch(e: Exception){
            Log.d("MAIN", "ISSUE: $e")
        }
        // our response is saved as a string and returned
        return response
    }

    private suspend fun getAdvice (result:String){
        withContext(Dispatchers.Main){


            val jsonObject= JSONObject(result)


            val date1  =jsonObject.getString("date")
            Log.i("date",date1.toString())
            var mulcu = jsonObject.getJSONObject("eur")

            for(i in mulcu.keys()){
                Log.i("datamulu",i)
                Log.i("value", mulcu!!.get(i).toString())



                arraydata.add(i.toString())
                arrayValue.add(mulcu.get(i).toString())
                listVersionAndroid.add(i)
                listVersionAndroid2.add(mulcu.get(i).toString().toDouble())
                val arrayAdapter = ArrayAdapter(
                        this@MainActivity2, android.R.layout.simple_list_item_1, listVersionAndroid)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner?.setAdapter(arrayAdapter)
                spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        selected = position
                     //   currencySelect = arraydata[position]
                        ///    spinner.tooltipText = currencySelect
                        var sate = spinner.selectedItem.toString()
                        selectedCur = listVersionAndroid2.get(position)


                    }


                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }



            }
            Log.i("arrayValue",arrayValue.toString())



            date.text = date1

        }
    }
    private fun loadJSON() {
        CoroutineScope(Dispatchers.IO).launch {
            // we fetch the data
            val data = async { fetchData() }.await()

            if(data.isNotEmpty()){
                getAdvice(data)
            }else{
                Log.d("MAIN", "Unable to get data")
            }
        }
    }
}
