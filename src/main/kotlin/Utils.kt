import java.io.File

fun getLinesFromFile(fileName: String) =
    File(fileName).useLines { it.toList() }

fun getCharMatrixFromFile(fileName: String) =
    getLinesFromFile(fileName)
        .map { it.toList() }
