package zb.club.memorycards.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index("id")])
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val word: String?,
    val example: String?,
    val picture: String?,
    val meaning: String?,
    val choosen: Boolean

):Parcelable
