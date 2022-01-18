package zb.club.memorycards.categories

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.canhub.cropper.CropImage
import zb.club.memorycards.R
import zb.club.memorycards.data.Category
import zb.club.memorycards.data.WordByCategory
import zb.club.memorycards.databinding.FragmentAddWordBinding
import kotlin.properties.Delegates


class AddWord : Fragment(), AdapterCategoryInWord.Onclicklistener {
    val arg: AddWordArgs by navArgs()
    lateinit var model: ViewModelCategory
    lateinit var adapter: AdapterCategoryInWord
    var listCat = mutableListOf<Category>()
    lateinit var arrayCategoryAdapter: ArrayAdapter<String>
    var catForAdapter = mutableListOf<String>()
    var idWord by Delegates.notNull<Long>()
    lateinit var imageView: ImageView
    lateinit var imageButtonDel: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddWordBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_word, container, false)
        model = ViewModelProvider(this).get(ViewModelCategory::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = model
        idWord = arg.word.id
        val wordByCat = WordByCategory (0, idWord, arg.category.idCategory)
        model.getCategoryForWord(idWord).observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()){if (arg.category.title != "all"){model.insertWordByCategory(wordByCat)}}
            adapter.setData(it)
        })
        imageButtonDel = binding.imageButtonDeletePictureWord
        imageView = binding.imageView4
        adapter = AdapterCategoryInWord(listCat, this)
        binding.recyclerViewCat.adapter = adapter
        binding.recyclerViewCat.layoutManager = LinearLayoutManager(requireContext())
        model.getIdedCategory.observe(viewLifecycleOwner
        ) {
            if (catForAdapter.isNullOrEmpty()) {
                for (i in it) {
                    i.title?.let { it1 -> catForAdapter.add(it1) }

                }
            }
            arrayCategoryAdapter = ArrayAdapter(requireContext(), R.layout.dropdoun, catForAdapter)
            binding.autoCompleteTextViewChooseCat.setAdapter(arrayCategoryAdapter)


        }
        model.getWordByID(idWord).observe(viewLifecycleOwner) {
            if (!it.picture.isNullOrEmpty() || it.picture != null) {
                binding.imageView4.load(it.picture.toUri())
            }
            if (!it.word.isNullOrEmpty() || it.word != null) {
                binding.inpetWordMain.setText(it.word)
            }
            if (!it.meaning.isNullOrEmpty() || it.meaning != null) {
                binding.inputAnother.setText(it.meaning)
            }

        }


        binding.imageButtonAddCat.setOnClickListener {
            val newCat = binding.autoCompleteTextViewChooseCat.text.toString().trim()
            if(!newCat.isEmpty()){
            model.getIdCat(newCat).observe(viewLifecycleOwner) {
                val wordByCat = WordByCategory(0, idWord, it)
                model.insertWordByCategory(wordByCat)
                adapter.notifyDataSetChanged()
            }}


        }
        imageButtonDel.setOnClickListener {
            imageButtonDel.visibility = View.INVISIBLE
            imageView.visibility = View.INVISIBLE
        model.updateWordPic(null.toString(), idWord)}
        binding.imageButtonSaveAnother.setOnClickListener { val word = binding.inpetWordMain.text.toString()
        val mean = binding.inputAnother.text.toString()
        model.updateWordWord(word, mean, idWord)
            val action = AddWordDirections.actionAddWordToWords2(arg.category)
        findNavController().navigate(action)}

        binding.addPictureWord.setOnClickListener {getImageClick() }





        return binding.root
    }



    fun getImageClick() {
        CropImage.activity().setAspectRatio(4, 3).setOutputCompressQuality(30)
            .start(requireContext(), this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri? = result?.uriContent
               model.updateWordPic(resultUri.toString(), idWord)
                imageView.visibility = View.VISIBLE
                imageButtonDel.visibility = View.VISIBLE

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result!!.error
            }
        }
    }

    override fun delCat(position: Int) {
        val category = adapter.getCategoryWordbyPosition(position)
         model.deleteWordByCategory(category.idCategory, idWord)
         adapter.notifyDataSetChanged()

    }



}