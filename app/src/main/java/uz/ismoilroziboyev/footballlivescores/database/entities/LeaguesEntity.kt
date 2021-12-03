package uz.ismoilroziboyev.footballlivescores.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class LeaguesEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val abbr: String,
    val image: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LeaguesEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (abbr != other.abbr) return false
        if (image != other.image) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + abbr.hashCode()
        result = 31 * result + image.hashCode()
        return result
    }
}