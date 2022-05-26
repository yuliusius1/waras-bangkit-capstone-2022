package com.yulius.warasapp.ui.diagnose.diagnose7

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityDiagnose7Binding
import com.yulius.warasapp.ml.DnnModel
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.diagnose6.Diagnose6Activity
import com.yulius.warasapp.util.ViewModelFactory
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ByteOrder.nativeOrder

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class Diagnose7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnose7Binding
    private lateinit var dataDiagnose: Diagnose
    private lateinit var viewModel: Diagnose7ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[Diagnose7ViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                binding.tvUsername.text = it.full_name
            }
        }
    }

    private fun getData() {
        dataDiagnose = intent.getParcelableExtra<Diagnose>("dataDiagnose") as Diagnose
    }

    private fun setupView() {
        supportActionBar?.hide()
        when(dataDiagnose.vomiting){
            1 -> binding.rbYes.isChecked = true
            else -> binding.rbNo.isChecked = true
        }
    }

    private fun setupAction() {
        binding.apply {
            rvSwitch.setOnCheckedChangeListener{ _, checkedId ->
                when(checkedId) {
                    R.id.rb_yes -> dataDiagnose.vomiting = 1
                    else -> dataDiagnose.vomiting = 0
                }
            }

            previousbtn.setOnClickListener {
                val intent = Intent(this@Diagnose7Activity, Diagnose6Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }

            submitBtn.setOnClickListener {
                prediction()
                val intent = Intent(this@Diagnose7Activity, Diagnose7Activity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun prediction() {
        Log.d("TAG", "prediction: ")
        val model = DnnModel.newInstance(applicationContext)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 1), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 40 * 40).apply { order(nativeOrder()) }
        byteBuffer.putFloat(dataDiagnose.fever.toFloat())
        byteBuffer.putFloat(dataDiagnose.cough.toFloat())
        byteBuffer.putFloat(dataDiagnose.tired.toFloat())
        byteBuffer.putFloat(dataDiagnose.soreThroat.toFloat())
        byteBuffer.putFloat(dataDiagnose.cold.toFloat())
        byteBuffer.putFloat(dataDiagnose.shortOfBreath.toFloat())
        byteBuffer.putFloat(dataDiagnose.vomiting.toFloat())

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
// Releases model resources if no longer used.
        Log.d("TAG", "prediction: $outputFeature0")

        model.close()
    }



}