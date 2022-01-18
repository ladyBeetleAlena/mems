package zb.club.memorycards.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index("idCategory")])
data class Category(
    @PrimaryKey(autoGenerate = true)

    val idCategory: Long,
    val title: String?,
    val picture: String?,
    val choose: Boolean

):Parcelable
