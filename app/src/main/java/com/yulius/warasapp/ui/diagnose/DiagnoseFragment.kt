package com.yulius.warasapp.ui.diagnose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentDiagnoseBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.diagnose1.Diagnose1Activity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory
import com.yulius.warasapp.util.getAges

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class DiagnoseFragment : Fragment() {
    private var _binding: FragmentDiagnoseBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataDiagnose : Diagnose
    private lateinit var viewModel: DiagnoseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        dataDiagnose = Diagnose(0,0,0,0,0,0,0,0,0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(
                    UserPreference.getInstance(
                        requireContext().dataStore
                    )
                )
            )[DiagnoseViewModel::class.java]

        viewModel.getUser().observe(viewLifecycleOwner){
            if(it.isLogin){
                binding.tvUsername.text = it.full_name
                dataDiagnose.age = getAges(it.date_of_birth)
            } else {
                val i = Intent(activity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
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
