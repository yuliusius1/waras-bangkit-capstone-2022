package com.yulius.warasapp.ui.diagnose

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.FragmentDiagnoseBinding
import com.yulius.warasapp.ui.diagnose.diagnose1.Diagnose1Activity
import com.yulius.warasapp.ui.main.MainActivity

class DiagnoseFragment : Fragment() {
    private var _binding: FragmentDiagnoseBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataDiagnose : Diagnose

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        dataDiagnose = Diagnose(0,0,0,0,0,0,0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.disagreeBtn.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.agreeBtn.setOnClickListener {
            val intent = Intent(activity, Diagnose1Activity::class.java)
            intent.putExtra("dataDiagnose", dataDiagnose)
            startActivity(intent)
        }
    }
}
