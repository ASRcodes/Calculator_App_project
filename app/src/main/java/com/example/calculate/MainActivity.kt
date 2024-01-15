package com.example.calculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculate.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //   Declaring sum Boolean variables
//   it will check if the last entered element was numeric or not
    var lastNumberic = false

    //    it check the state errors
    var stateErroe = false

    //    it check if the last entered digit was dot it will track that
    var lastDot = false

    //    Variable for the library we have defined
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClearClick(view: View) {
//        If clicked on clear we'll clear the data from the data_tv
        binding.dataTv.text = ""
        lastNumberic = false
    }

    fun onBackClick(view: View) {
//        When clicked on back button we'll delete the last button over there
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
//        Now inside the try and catch block we'll check if the last character is not a operator if its operator we'll not perform the operation
//        And if this digit then we'll call the OnEqual methoddd
        try {
//            Determining the last character
            val lastchar = binding.dataTv.text.toString().last()
            if (lastchar.isDigit()) {
                OnEqual()
            }
        } catch (e: Exception) {
            binding.resultTv.text = ""
//            when the data will be cleared from the data_tv the visibility of result_tv will be gone
            binding.resultTv.visibility = View.GONE

            Log.e("last char error ", e.toString())
        }
    }

    fun onOperatorClick(view: View) {

//        Check for stateError and lstNUmeric
        if (!stateErroe && lastNumberic) {

//            Then we'll append the data of button or the txt of button i.e, 1,2,3,4,etc. on TextView data_tv
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumberic = false
            OnEqual()
        }
    }

    fun onDigitClick(view: View) {
//        first we'll check if it not the case of digit error
        if (stateErroe) {
//            we'll put that error over our textView
            binding.dataTv.text = (view as Button).text
//            resetting the stateError
            stateErroe = false
        } else {
//            if everything is alright then take that button's text and put it there for further evaluation
            binding.dataTv.append((view as Button).text)
        }
//            Set that lastNumeric = true
        lastNumberic = true
//        call evaluation fun
        OnEqual()

    }

    fun onEqualClick(view: View) {
//        When equal button is clicked we'll set the data to the result_tv
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }

    fun onAllClearClick(view: View) {
//        Here if All clear is clicked we have to clear everything and setUp as that app was started new
        binding.resultTv.text = ""
        binding.dataTv.text = ""
        stateErroe = false
        lastNumberic = false
        lastDot = false
        binding.resultTv.visibility = View.GONE

    }

    //This function will do the evaluation part
    fun OnEqual() {
//        Here now we'll check first that the entered value is numeric and with no error in if block
        if (lastNumberic && !stateErroe) {

//            We'll fetch the text from the text view data
            val txt = binding.dataTv.text.toString();
//            Now we'll give that text value to our expression variable which we have imported from its library and it will evalute the value and give us that
            expression = ExpressionBuilder(txt).build()

//            Now we'll put the result in try and catch block because if the imported function will cause some error then it will handle that
            try {
//                evaluating the result
                val result = expression.evaluate()
//                Showing the result over the result textView
                binding.resultTv.visibility = View.VISIBLE
//                Showing the result with = in before
                binding.resultTv.text = "=" + result.toString()
            } catch (ex: ArithmeticException) {
                Log.e("Evaluation error", ex.toString())
//                Now setting that error over our result textView
                binding.resultTv.text = "Error"
//                making the state error true to show errors
                stateErroe = true
//                Resetting the lastNumeric
                lastNumberic = false
            }
        }
    }
}