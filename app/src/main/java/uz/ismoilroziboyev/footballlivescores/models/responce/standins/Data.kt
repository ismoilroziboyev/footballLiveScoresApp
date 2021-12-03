package uz.ismoilroziboyev.footballlivescores.models.responce.standins

data class Data(
    val abbreviation: String,
    val name: String,
    val season: Int,
    val seasonDisplay: String,
    val standings: List<Standing>
)