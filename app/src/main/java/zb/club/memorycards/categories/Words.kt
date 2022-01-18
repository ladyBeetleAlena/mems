package zb.club.memorycards.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zb.club.memorycards.R
import zb.club.memorycards.data.Word
import zb.club.memorycards.databinding.FragmentWordsBinding


class Words : Fragment(), AdapterWord.WordClick{

    val args: WordsArgs by navArgs()
    lateinit var model: ViewModelCategory
    lateinit var adapter:AdapterWord
    val wordList = mutableListOf<Word>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWordsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_words, container, false)
        model = ViewModelProvider(this).get(ViewModelCategory::class.java)
        adapter = AdapterWord(wordList,this)
        binding.lifecycleOwner = this
        binding.viewmodel = model
        binding.words.adapter = adapter
        binding.words.layoutManager = LinearLayoutManager(requireContext())
        if(args.category.title == "all"){model.getWord.observe(viewLifecycleOwner) {

                adapter.setData(it)
            }

         }else{model.getWordByCat(args.category.idCategory).observe(viewLifecycleOwner) {

                adapter.setData(it)

        }
        }

        binding.floatingActionNewWord.setOnClickListener {
            val wordnew = Word(0,null, null, null, null, false)
            model.insertWord(wordnew)
            model.statusWord.observe(viewLifecycleOwner) {
                if (it == true) {
                    addWord()
                }
            }

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
                    val word:Word = adapter.getwordByPosition(viewHolder.adapterPosition)
                    model.delWord(word)


                    Toast.makeText(
                        requireContext(),
                        "Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    adapter.notifyDataSetChanged()
                }


            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.words)




        return binding.root
    }

    fun addWord(){
        model.getlastWord.observe(viewLifecycleOwner) {
            val action = WordsDirections.actionWords2ToAddWord(args.category, it)
            model.setStatusWord(false)
            findNavController().navigate(action)
        }


    }

    override fun editWord(position: Int) {
        val word = adapter.getwordByPosition(position)
        val action = WordsDirections.actionWords2ToAddWord(args.category, word )

        findNavController().navigate(action)

    }


}