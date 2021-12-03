package uz.ismoilroziboyev.footballlivescores.models.responce.standins

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ResStandingsData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val `data`: Data,
    val status: Boolean,
)