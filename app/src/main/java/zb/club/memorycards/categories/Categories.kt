package zb.club.memorycards.categories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import zb.club.memorycards.R
import zb.club.memorycards.data.Category
import zb.club.memorycards.databinding.FragmentCategoriesBinding


class Categories : Fragment() {
    lateinit var adapter:CategoriesRecyclerAdapter
    val listCategory = mutableListOf<Category>()
     lateinit var model: ViewModelCategory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_categories, container, false)
        model = ViewModelProvider(this).get(ViewModelCategory::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = model
        val img: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.smile)
        binding.imageCat.load(img){transformations(CircleCropTransformation())}

        binding.floatingActionButton.setOnClickListener {

            val categories = Category (0, null, null , false )
            model.saveCat(categories)
            model.status.observe(viewLifecycleOwner) {
                if (it == true) {
                    val idcat: Long? = model.catNumber.value
                    if (idcat != null) {
                        changeCategory(idcat)
                        }
                        model.setStatus(false)
                    }

                }}
        adapter = CategoriesRecyclerAdapter(listCategory)
        binding.recyclerCategory.adapter = adapter
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        model.getIdedCategory.observe(viewLifecycleOwner) {
            for(i in 0 until it.size){
                if(it[i].title.isNullOrEmpty()&& it[i].picture.isNullOrEmpty()){model.deleteCategory(it[i])}
            }
            if (listCategory.isNullOrEmpty()){adapter.setData(it)}
        }


        binding.cardView2.setOnClickListener {
            val cat = Category(0, "all", null, false)
            val action = CategoriesDirections.actionCategoriesToWords2(cat)
            findNavController().navigate(action)
        }
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    model.deleteCategory(adapter.getCategoryByPosition(viewHolder.adapterPosition))

                    Toast.makeText(
                        requireContext(),
                       "Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerCategory)



        return binding.root
    }



    fun changeCategory(idCat:Long){
 try {
     val action = CategoriesDirections.actionCategoriesToAddCategory(idCat)
     findNavController().navigate(action)
 }catch (e:Exception){}







    }

}