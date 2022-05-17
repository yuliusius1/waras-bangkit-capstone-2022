package com.yulius.warasapp.ui.profile.editprofile

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityEditProfileBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    var calender: Calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var viewModel : EditProfileViewModel
    private lateinit var user : User

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.no_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {

        binding.apply {
            dateEditText.setOnClickListener {
                DatePickerDialog(
                    this@EditProfileActivity,
                    dateSetListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            btnGallery.setOnClickListener {
                startGallery()
            }

            btnCamera.setOnClickListener {
                if (!allPermissionsGranted()) {
                    ActivityCompat.requestPermissions(
                        this@EditProfileActivity,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                }
                startTakePhoto()
            }

            saveButton.setOnClickListener {
                var isError = false
                if (TextUtils.isEmpty(nameEditTextLayout.editText?.text)){
                    isError = true
                    nameEditTextLayout.editText?.error = getString(R.string.enter_name)
                }

                if (TextUtils.isEmpty(usernameEditTextLayout.editText?.text)){
                    isError = true
                    usernameEditTextLayout.editText?.error = getString(R.string.enter_username)
                }

                if (TextUtils.isEmpty(emailEditTextLayout.editText?.text)){
                    isError = true
                    emailEditTextLayout.editText?.error = getString(R.string.enter_email)
                }

                if (TextUtils.isEmpty(dateEditTextLayout.editText?.text)){
                    isError = true
                    dateEditTextLayout.editText?.error = getString(R.string.enter_date)
                }

                if (TextUtils.isEmpty(telpEditTextLayout.editText?.text)){
                    isError = true
                    telpEditTextLayout.editText?.error = getString(R.string.enter_tel)
                }

                if (!isError){
                    viewModel.checkUser(usernameEditTextLayout.editText?.text.toString())
                    viewModel.isFound.observe(this@EditProfileActivity){
                        if (!it){
                            viewModel.saveUser(
                                User(
                                    nameEditTextLayout.editText?.text.toString(),
                                    usernameEditTextLayout.editText?.text.toString(),
                                    emailEditTextLayout.editText?.text.toString(),
                                    user.password,
                                    telpEditTextLayout.editText?.text.toString(),
                                    dateEditTextLayout.editText?.text.toString(),
                                    true
                                )
                            )
                        }
                        showDialogs(it)
                    }
                }
            }

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[EditProfileViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                user = it
                binding.apply {
                    nameEditTextLayout.editText?.setText(it.name)
                    usernameEditTextLayout.editText?.setText(it.username)
                    emailEditTextLayout.editText?.setText(it.email)
                    dateEditTextLayout.editText?.setText(it.birth)
                    telpEditTextLayout.editText?.setText(it.telephone)
                }
            }
        }
    }

    private fun setupView() {
        supportActionBar?.title = getString(R.string.title_edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, monthOfYear)
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateDateInView() {
        val myFormat = getString(R.string.date_formate)
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateEditText.setText(sdf.format(calender.time))
    }
    
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose))
        launcherIntentGallery.launch(chooser)
    }
    
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@EditProfileActivity,
                "com.yulius.warasapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }

    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
//            val myFile = uriToFile(selectedImg, this@EditProfileActivity)
            binding.imgPhoto.setImageURI(selectedImg)
        }
    }
    
    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            val result = BitmapFactory.decodeFile(myFile.path)

            binding.imgPhoto.setImageBitmap(result)
        }
    }

    private fun showDialogs(status: Boolean) {
        if (!status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                val message = getString(R.string.edit_profile_success)
                setMessage(message)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    startActivity(Intent(this@EditProfileActivity, MainActivity::class.java))
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                val message = getString(R.string.login_error)
                setMessage(message)
                setNegativeButton(getString(R.string.repeat)) { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}
