package uz.ismoilroziboyev.footballlivescores.models.responce.standins

data class Standing(
    val note: Note,
    val stats: List<Stat>,
    val team: Team
)