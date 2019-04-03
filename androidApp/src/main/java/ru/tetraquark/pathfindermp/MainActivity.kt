package ru.tetraquark.pathfindermp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfindermp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(TestHello().multiplatformHello())
    }
}
