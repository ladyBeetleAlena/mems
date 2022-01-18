package zb.club.memorycards.categories

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import zb.club.memorycards.R
import zb.club.memorycards.data.Word

class AdapterWord(var word: MutableList<Word>, val listener: WordClick): RecyclerView.Adapter<AdapterWord.WordVH>() {

    lateinit var textUnderpic:TextView
    lateinit var image:ImageView
    lateinit var card:CardView
    lateinit var anotherSidText:TextView
    inner class WordVH(itemView: View): RecyclerView.ViewHolder(itemView), View.OnLongClickListener{
        init {

            textUnderpic = itemView.findViewById(R.id.textViewUnderPic)
            image = itemView.findViewById(R.id.imageViewFlip)
            card = itemView.findViewById(R.id.cardWord)
            anotherSidText = itemView.findViewById(R.id.textViewMeaning)
            itemView.setOnLongClickListener(this)

            card.setOnClickListener {
                val textone: TextView = it.findViewById(R.id.textViewUnderPic)
                val texttwo:TextView = it.findViewById(R.id.textViewMeaning)
                val imageThis: ImageView = it.findViewById(R.id.imageViewFlip)
                try {
                    val flipOutAnimatorSet =
                        AnimatorInflater.loadAnimator(
                            it.context,
                            R.animator.flipflop
                        ) as AnimatorSet
                    flipOutAnimatorSet.setTarget(it)
                    flipOutAnimatorSet.start()
                    if(textone.visibility == View.VISIBLE){

                        if(imageThis.visibility != View.GONE){imageThis.visibility = View.INVISIBLE}
                        textone.visibility = View.INVISIBLE
                        texttwo.visibility = View.VISIBLE


                    } else {

                        if(imageThis.visibility != View.GONE){imageThis.visibility = View.VISIBLE}
                        texttwo.visibility = View.INVISIBLE
                        textone.visibility = View.VISIBLE



                    } } catch (e: Exception) {

                }
            }

        }


        override fun onLongClick(p0: View?): Boolean {

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.editWord(position)
            }
            return true

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWord.WordVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return WordVH(itemView)
    }

    override fun onBindViewHolder(holder: WordVH, position: Int) {

       val currentItem = word[position]

        textUnderpic.setText(currentItem.word)
        textUnderpic.visibility = View.VISIBLE
        anotherSidText.setText(currentItem.meaning)
        anotherSidText.visibility = View.INVISIBLE
        if(currentItem.picture.isNullOrEmpty() || currentItem.picture == null){image.visibility = View.GONE
        }else{image.visibility = View.VISIBLE
        image.load(currentItem.picture.toUri())}


        }











    override fun getItemCount(): Int {
      return word.size
    }

    fun setData(word:List<Word>){
        this.word = word as MutableList<Word>
        notifyDataSetChanged()
    }

    fun getwordByPosition(position: Int):Word{
        return word[position]
    }
    interface WordClick{
        fun editWord(position: Int)
    }












}