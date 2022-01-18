package zb.club.memorycards.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idWord"), Index("idCategor")],
    foreignKeys = [(ForeignKey(entity = Word::class,
parentColumns = ["id"],
childColumns = ["idWord"],
onDelete = CASCADE)),
    (ForeignKey(entity = Category::class,
    parentColumns = ["idCategory"],
    childColumns = ["idCategor"],
    onDelete = CASCADE)

)]


)
data class WordByCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idWord: Long,
    val idCategor: Long

)
