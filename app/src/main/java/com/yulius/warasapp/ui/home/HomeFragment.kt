package com.yulius.warasapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentHomeBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.recommendation.RecommendationActivity
import com.yulius.warasapp.ui.profile.editprofile.EditProfileActivity
import com.yulius.warasapp.util.ViewModelFactory
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.ArrayList


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.actionBar?.hide()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
        setupAction()
        getRandomQuote()
    }

    private fun getRandomQuote() {
        val client = AsyncHttpClient()
        val url = "https://quote-api.dicoding.dev/random"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val quote = responseObject.getString("en")
                    val author = responseObject.getString("author")
                    binding.tvQuote.text = quote
                    binding.tvQuoteAuthor.text = author
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupView() {
        binding.ivAvatar.setImageResource(R.drawable.avatar)
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
            )[HomeViewModel::class.java]

        viewModel.getUser().observe(viewLifecycleOwner){
            if(it.isLogin){
                binding.tvUsername.text = it.full_name
            } else {
                val i = Intent(activity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }

    private fun setupAction() {
        binding.btnCheck.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_diagnose)
        }

        binding.tvRecommendations1.setOnClickListener {
            startActivity(Intent(context,RecommendationActivity::class.java))
        }

        binding.ivAvatar.setOnClickListener{
            val popupMenu = PopupMenu(context,binding.ivAvatar)
            popupMenu.inflate(R.menu.popup_profile_menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.logout -> {
                        viewModel.logout()
                    }
                    R.id.edit_profile -> {
                        startActivity(Intent(activity, EditProfileActivity::class.java))
                    }
                }
                true
            }
            popupMenu.show()


        }
    }


}