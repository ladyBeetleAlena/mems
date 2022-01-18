package zb.club.memorycards.data

import androidx.lifecycle.LiveData

class Repository(private val wordDao: WordDao) {
      val getdWords: LiveData<List<Word>> = wordDao.getAWords()
      val getIdedCategory: LiveData<List<Category>> = wordDao.getIdedCategory()
     val getLastWord: LiveData<Word> = wordDao.getLastWord()

    suspend fun deleteWord(word: Word){
        wordDao.deleteWord(word)
    }
    fun getWordByCategory(idCat:Long):LiveData<List<Word>>{
        return wordDao.getWordByCat(idCat)
    }


    fun getIdCat(title:String): LiveData<Long>{
        return wordDao.getIDcat(title)
    }

    fun getCategoryForWord(idword: Long): LiveData<List<Category>>{
        return wordDao.getCatForWord(idword)
    }
    suspend fun insertCategory(category: Category): Long{
        return wordDao.insertCategory(category)
    }

    suspend fun insertWordByCategory(wordByCategory: WordByCategory){
        wordDao.insertWordByCategory(wordByCategory)
    }
    suspend fun deletWordByCategory(idCat:Long, idWord:Long){
        wordDao.deleteWordByCategory(idCat, idWord)
    }

     fun getCategoryById(id:Long):LiveData<Category>{
        return wordDao.getCategoryById(id)
    }


    suspend fun updateCatecory(category: Category){
        wordDao.updateCategory(category)
    }

    suspend fun updateCategoryPicture(newpic:String, idcat:Long){
        wordDao.updateCategoryPicture(newpic,idcat )
    }
    suspend fun updateCategoryTitle(newtitle:String, idcat:Long){
        wordDao.updatecategoryTitle(newtitle, idcat)
    }

    suspend fun deletCategory(cat:Category){
        wordDao.deleteCategory(cat)
    }

    suspend fun insertWord(word:Word){
        wordDao.insertWord(word)
    }

    suspend fun updateWordWord (word:String, mean: String, id:Long){
        wordDao.updateWordWord(word, mean, id)
    }

    suspend fun updatewordPic(pic:String, id:Long){
     wordDao.updateWordPicture(pic, id)
    }
   fun getWordById(id:Long):LiveData<Word>{
      return wordDao.getWordById(id)
   }



}