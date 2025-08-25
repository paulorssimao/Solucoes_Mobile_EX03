package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }

    fun appendValue(value: String) { input += value }
    fun clear() { input = "" }
    fun calculate() {
        try {
            val result = eval(input)
            input = result.toString()
        } catch (e: Exception) {
            input = "Error"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = input,
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            maxLines = 1
        )

        val buttons = listOf(
            listOf("7","8","9","/"),
            listOf("4","5","6","*"),
            listOf("1","2","3","-"),
            listOf("C","0","=","+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when(label) {
                                "C" -> clear()
                                "=" -> calculate()
                                else -> appendValue(label)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = label, fontSize = 24.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

fun eval(expr: String): Double {
    val engine = javax.script.ScriptEngineManager().getEngineByName("rhino")
    return engine.eval(expr) as Double
}