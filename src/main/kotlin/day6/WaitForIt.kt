package day6

import getLinesFromFile

fun main() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day6\\input.txt")
    part1(lines)
    println()
    part2(lines)
}

fun part1(lines: List<String>) {
    val numberRegex = "\\d+".toRegex()
    val times = numberRegex.findAll(lines[0]).map { it.value.toInt() }
    val distances = numberRegex.findAll(lines[1]).map { it.value.toInt() }

    times.zip(distances).map { (time, distance) ->
        mostMillis(time.toLong(), distance.toLong()) - leastMillis(time.toLong(), distance.toLong()) + 1
    }
        .reduce { acc, i -> acc * i }
        .let { print(it) }
}

fun part2(lines: List<String>) {
    val numberRegex = "\\d+".toRegex()
    val time = numberRegex.findAll(lines[0])
        .map { it.value }
        .joinToString(separator = "") { it }
        .toLong()
    val distance = numberRegex.findAll(lines[1])
        .map { it.value }
        .joinToString(separator = "") { it }
        .toLong()


    print(mostMillis(time, distance) - leastMillis(time, distance) + 1)
}

fun leastMillis(time: Long, distance: Long): Long {
    (1..<time).forEach { t ->
        if ((time - t) * t > distance) {
            return t
        }
    }

    return 0
}

fun mostMillis(time: Long, distance: Long): Long {
    (time - 1 downTo 1).forEach { t ->
        if (t * (time - t) > distance) {
            return t
        }
    }

    return 0
}