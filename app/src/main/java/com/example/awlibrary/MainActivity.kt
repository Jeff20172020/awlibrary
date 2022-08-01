package com.example.awlibrary

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aw_library.log.AwLog
import com.example.aw_library.log.AwLogConfig
import com.example.aw_library.log.AwLogManager
import com.example.aw_library.log.JsonParser
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    var str: String = "1512234818665353216_P00000012915_1037101FW1000100000__";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AwLogManager.init(object : AwLogConfig() {

            override fun enable(): Boolean {
                return true
            }

            override fun getJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }


        })


        findViewById<Button>(R.id.print).setOnClickListener {
            AwLog.et("Myname is","what the fuck")

            val split = str.split("_")
            println("splite---${split.size}")

        }


    }
}