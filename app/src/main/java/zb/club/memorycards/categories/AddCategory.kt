package zb.club.memorycards.categories

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImage
import zb.club.memorycards.R
import zb.club.memorycards.databinding.FragmentAddCategoryBinding
import kotlin.properties.Delegates


class AddCategory : Fragment() {

    val args: AddCategoryArgs by navArgs()
    val REQUEST_IMAGE_CAPTURE = 1
    var idCat by Delegates.notNull<Long>()
    lateinit var titleOld: String

    lateinit var model: ViewModelCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddCategoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_category, container, false)
        model = ViewModelProvider(this).get(ViewModelCategory::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = model

        idCat = args.idCat
        model.getCatbyId(idCat).observe(viewLifecycleOwner) {
            binding.inputword.setText(it.title)
            titleOld = it.title.toString()
            if(!it.picture.isNullOrEmpty()){binding.imageViewCategory.setImageURI(it.picture?.toUri())}
        }

       binding.imageButtonSave.setOnClickListener {


       }

        binding.imageButtonTakepicture.setOnClickListener {getImageClick() }
        binding.imageButtonSave.setOnClickListener{
            val newTitle = binding.inputword.text.toString()
            model.updateCategoryTitle( newTitle, idCat)

            findNavController().navigate(R.id.action_addCategory_to_categories)
        }









      
        return binding.root
    }
    fun getImageClick() {
        CropImage.activity().setAspectRatio(1, 1).setOutputCompressQuality(20)
            .start(requireContext(), this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri? = result?.uriContent
                model.updateCategoryPicture(resultUri.toString(), idCat)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result!!.error
            }
        }
    }



}