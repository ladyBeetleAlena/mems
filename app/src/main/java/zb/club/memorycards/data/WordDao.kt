package zb.club.memorycards.data

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface WordDao {

    @Query("SELECT * FROM word ORDER BY id ASC ")
    fun getAWords(): LiveData<List<Word>>

    @Query("SELECT * FROM category ORDER BY idCategory ASC")
    fun getIdedCategory(): LiveData<List<Category>>


    @Query("SELECT idCategory, title, picture, choose FROM category JOIN wordbycategory on  idCategor = idCategory WHERE idWord = :idWord")
    fun getCatForWord(idWord:Long):LiveData<List<Category>>

    @Query("SELECT word.id, word, example, picture, meaning, choosen FROM word JOIN wordbycategory on word.id=idWord WHERE idCategor = :idCat")
    fun getWordByCat(idCat:Long): LiveData<List<Word>>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category):Long
    @Insert
    suspend fun insertWordByCategory(wordByCategory: WordByCategory)


    @Delete
    suspend fun deleteCategory(category: Category)

    @Delete
    suspend fun deleteWord(word:Word)

    @Query("DELETE FROM wordbycategory WHERE idCategor = :idCat AND idWord = :idWord")
    suspend fun deleteWordByCategory(idCat: Long, idWord: Long)

    @Query("SELECT idCategory FROM category WHERE title LIKE :title LIMIT 1")
    fun getIDcat(title:String): LiveData<Long>

    @Update
    suspend fun updateWord(word: Word)
    @Update
    suspend fun updateCategory(category: Category)



    @Query("SELECT * FROM category WHERE idCategory = :id")
    fun getCategoryById(id:Long): LiveData<Category>

    @Query("Update category SET picture = :newpic WHERE idCategory = :idcategory")
    suspend fun updateCategoryPicture (newpic:String, idcategory:Long)

    @Query("Update category SET title = :newtitle WHERE idCategory = :idcategory")
    suspend fun updatecategoryTitle(newtitle:String, idcategory:Long)



    @Query("SELECT * FROM word  ORDER BY ID DESC LIMIT 1")
    fun getLastWord():LiveData<Word>


    @Query("Update word SET picture = :newpic WHERE id = :idword")
    suspend fun updateWordPicture (newpic:String, idword:Long)

    @Query("Update word SET word = :newWord,  meaning = :newmean WHERE id = :idWord")
    suspend fun updateWordWord (newWord:String, newmean:String, idWord:Long)

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWordById(id: Long): LiveData<Word>









}