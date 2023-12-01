import java.io.File

fun getLinesFromFile(fileName: String) =
    File(fileName).useLines { it.toList() }
