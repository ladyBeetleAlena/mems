package zb.club.memorycards.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import zb.club.memorycards.R
import zb.club.memorycards.data.Category

class CategoriesRecyclerAdapter(var categories: MutableList<Category>): RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoriesVH>() {
    class CategoriesVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView
        val imageView: ImageView
        init {
            textView = itemView.findViewById(R.id.textViewCat)
            imageView = itemView.findViewById(R.id.imageViewCatPic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoriesVH(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
       val currentItem = categories[position]
        holder.textView.setText(currentItem.title)
        if (!currentItem.picture.isNullOrEmpty()){holder.imageView.load(currentItem.picture.toUri()){transformations(CircleCropTransformation())}}
        holder.itemView.setOnClickListener { view -> val action =
            currentItem.let { it1 -> CategoriesDirections.actionCategoriesToWords2(it1)
            }
            if (action != null) {
                view.findNavController().navigate(action)
            }



        }



    }

    override fun getItemCount(): Int {
      return categories.size
    }

    fun setData(category: List<Category>){
        this.categories = category as MutableList<Category>
        notifyDataSetChanged()
    }

    fun getCategoryByPosition (position:Int): Category{
        return categories[position]

    }


}