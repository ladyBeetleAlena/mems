package zb.club.memorycards.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import zb.club.memorycards.R
import zb.club.memorycards.data.Category

class AdapterCategoryInWord(var cat: MutableList<Category>, val listener: AdapterCategoryInWord.Onclicklistener):RecyclerView.Adapter<AdapterCategoryInWord.CatWordVH>() {

    inner class CatWordVH(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
          val imadeDelCat:ImageView
          val textCat:TextView
         val textCatN:TextView
        init {
            imadeDelCat = itemView.findViewById(R.id.imageButtonDeleteFromCat)
            textCat = itemView.findViewById(R.id.textViewCatInWord)
            textCatN = itemView.findViewById(R.id.textViewNumberCat)
            imadeDelCat.setOnClickListener(this)

        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.delCat(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatWordVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_in_word, parent, false)
        return CatWordVH(itemView)
    }

    override fun onBindViewHolder(holder: CatWordVH, position: Int) {
        val currentItem = cat[position]
       holder.textCatN.setText(currentItem.idCategory.toString())
        holder.textCat.setText(currentItem.title)

    }

    override fun getItemCount(): Int {
       return cat.size
    }

    fun setData(cat:List<Category>){
        this.cat = cat as MutableList<Category>
        notifyDataSetChanged()
    }

    fun getCategoryWordbyPosition(position: Int): Category{
        return cat[position]
    }

   interface Onclicklistener{
       fun delCat(position: Int)


   }
}