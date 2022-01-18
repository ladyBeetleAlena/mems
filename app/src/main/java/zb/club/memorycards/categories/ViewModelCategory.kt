package zb.club.memorycards.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zb.club.memorycards.data.*

class ViewModelCategory(application: Application): AndroidViewModel(application) {
    private val repository:Repository
    val getIdedCategory: LiveData<List<Category>>
    val getlastWord:LiveData<Word>
    val getWord: LiveData<List<Word>>
    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = Repository(wordDao)
       getIdedCategory = repository.getIdedCategory
        getlastWord = repository.getLastWord
        getWord = repository.getdWords

    }

    fun getWordByCat(idCat:Long):LiveData<List<Word>>{
        return repository.getWordByCategory(idCat)
    }
    fun getIdCat(title:String):LiveData<Long>{
        return repository.getIdCat(title)
    }

    private var _status = MutableLiveData<Boolean?>(false)
    val status: LiveData<Boolean?>
    get() = _status
    fun setStatus(st: Boolean) {
        _status.value = st
    }
    private var _catNumber = MutableLiveData<Long>()
    val catNumber: LiveData<Long>
        get() = _catNumber
    fun setId(idCat: Long) {
        _catNumber.value = idCat
    }

    fun saveCat(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            var value: Long = 0
            value = repository.insertCategory(category)
            _catNumber.postValue(value)
            _status.postValue(true)
        } }

    fun getCatbyId(id:Long):LiveData<Category>{
       return repository.getCategoryById(id)
    }

    val categoryforWork: LiveData<Category>? = catNumber.value?.let { getCatbyId(it) }
    val titleCategory = categoryforWork?.value?.title


    fun updateCategoryPicture(newpic: String, idcat:Long){
        viewModelScope.launch(Dispatchers.IO) {
        repository.updateCategoryPicture(newpic,idcat )}
    }
    fun updateCategoryTitle(newtitle:String, idcat:Long){
        viewModelScope.launch(Dispatchers.IO) {
        repository.updateCategoryTitle(newtitle, idcat)}
    }
    fun deleteCategory(cat:Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletCategory(cat)}
    }

    fun deleteWordByCategory(idCat:Long, idWord:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletWordByCategory(idCat, idWord)}
    }
    fun insertWordByCategory(wordByCategory: WordByCategory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWordByCategory(wordByCategory)}
    }
    fun getCategoryForWord(id:Long): LiveData<List<Category>>{
        return  repository.getCategoryForWord(id)
    }




    private var _statusWord = MutableLiveData<Boolean?>(false)
    val statusWord: LiveData<Boolean?>
        get() = _statusWord
    fun setStatusWord(st: Boolean) {
        _statusWord.value = st
    }

    fun insertWord(word:Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWord(word)
        _statusWord.postValue(true)}
    }

    fun updateWordPic(pic: String, id:Long){
        viewModelScope.launch(Dispatchers.IO) {

                repository.updatewordPic(pic, id )


    }}

    fun updateWordWord(word: String, mean: String,  id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWordWord(word, mean, id )

        }}

   fun getWordByID(id: Long): LiveData<Word>{
       return repository.getWordById(id)
   }

    fun delWord(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(word)

        }}


    private val _m =  MutableLiveData(0)
    val m: MutableLiveData<Int> = _m
    fun plas(){ if (_m.value != 10){_m.value = _m.value?.plus(1)}else {_m.value = 0}
    }



}