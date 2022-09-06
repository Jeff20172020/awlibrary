package com.example.awlibrary

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aw_library.log.*
import com.google.gson.Gson
import com.taobao.sophix.SophixManager
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var viewPrinterProvider: AwViewPrinterProvider

    var str: String = "1512234818665353216_P00000012915_1037101FW1000100000__";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        SophixManager.getInstance().queryAndLoadNewPatch()

        AwLogManager.init(object : AwLogConfig() {

            override fun enable(): Boolean {
                return true
            }

            override fun getJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }


        })

        val awViewPrinter = AwViewPrinter(this)
        AwLogManager.getInstance().addPrinter(awViewPrinter)
        AwLogManager.getInstance().addPrinter(AwFilePrinter.getInstance(externalCacheDir?.absolutePath+ File.separator+"log",1000L))


        viewPrinterProvider = awViewPrinter.viewPrinterProvider

        viewPrinterProvider.showFloatingView()


        findViewById<Button>(R.id.print).setOnClickListener {
            AwLog.dt("Myname is", "what the fuck")


        }


    }
}