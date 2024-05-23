package choehaualen.breath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An entity representing the ***User*** data stored inside a ***user_table*** database table.
 *
 * @property id The unique ID of the User (automatically generated when created).
 * @property name The unique name of the User.
 */
@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String

    // Will add more properties later on ...

)