package com.bintangfajarianto.submission2.ui.publish

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.databinding.ActivityPublishBinding
import com.bintangfajarianto.submission2.di.ViewModelFactory
import com.bintangfajarianto.submission2.utils.ImageHandler
import com.bintangfajarianto.submission2.utils.ImageHandler.reduceFileImage
import com.google.android.material.snackbar.Snackbar
import java.io.File

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding
    private lateinit var currentPhotoPath: String
    private val publishViewModel : PublishViewModel by viewModels {
        ViewModelFactory(this)
    }

    private var getFile: File? = null
    private var token: String? = null

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.imgPublish.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = ImageHandler.uriToFile(selectedImg, this)
            getFile = myFile
            binding.imgPublish.setImageURI(selectedImg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

        publishViewModel.getToken().observe(this) { token ->
            this.token = token
        }

        // Show Loading
        publishViewModel.isLoading.observe(this) {
            setLoading(it)
        }

        setUpAction()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        ImageHandler.createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@PublishActivity,
                "com.bintangfajarianto.submission1",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun publish() {
        if (getFile != null && binding.publishDescription.text.toString().isNotBlank()) {
            val file = reduceFileImage(getFile as File)

            publishViewModel.upload(
                file,
                binding.publishDescription.text.toString(),
                resources.getString(R.string.token, token)
            )

            // Handle Error Responses
            publishViewModel.message.observe(this) { event ->
                event.getContentIfNotHandled()?.let { text ->
                    Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                }
            }

            // Handle Success Responses
            publishViewModel.responsePublish.observe(this) {
                Toast.makeText(
                    this@PublishActivity,
                    R.string.upload_valid,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        } else {
            Toast.makeText(
                this@PublishActivity,
                R.string.upload_invalid,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpAction() {
        binding.apply {
            btnCamera.setOnClickListener { openCamera() }
            btnGallery.setOnClickListener { openGallery() }
            btnPublish.setOnClickListener { publish() }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnPublish.isEnabled = !isLoading
            btnGallery.isEnabled = !isLoading
            btnCamera.isEnabled = !isLoading
            publishDescription.isEnabled = !isLoading
        }
    }
}