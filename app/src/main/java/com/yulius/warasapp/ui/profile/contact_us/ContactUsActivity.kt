package com.yulius.warasapp.ui.profile.contact_us

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityContactUsBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.util.ViewModelFactory
import java.util.ArrayList

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContactUsBinding
    private lateinit var viewModel: ContactUsViewModel
    private lateinit var rvContact: RecyclerView
    private val list = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        
        rvContact = binding.rvContact
        rvContact.setHasFixedSize(true)

        list.addAll(listContacts)
        showRecyclerList()
    }

    private fun setupView() {
        supportActionBar?.title = getString(R.string.title_contact_us)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ContactUsViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }
    
    private val listContacts: ArrayList<Contact>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_email)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listContact = ArrayList<Contact>()
            for (i in dataName.indices) {
                val contact = Contact(dataName[i],dataDescription[i], dataPhoto.getResourceId(i, -1))
                listContact.add(contact)
            }
            return listContact
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvContact.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvContact.layoutManager = LinearLayoutManager(this)
        }
        val listContactAdapter = ListContactAdapter(list)
        rvContact.adapter = listContactAdapter

    }

    private fun setupAction() {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
