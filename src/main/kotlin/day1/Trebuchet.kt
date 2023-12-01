package day1

import getLinesFromFile

val numbers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun getSpelledDigitOrNull(text: String, index: Int): String? =
    numbers.keys.firstOrNull {
        index + it.length <= text.length && text.substring(index, index + it.length) == it
    }

fun parseDigit(digit: String?) =
    if (digit != null && numbers.containsKey(digit))  {
        numbers[digit]
    } else {
        null
    }

fun getFirstDigit(text: String) =
    text.mapIndexedNotNull { index, it ->
        if (it.isDigit()) {
            it
        } else {
            parseDigit(getSpelledDigitOrNull(text, index))
        }
    }.first()

fun getLastDigit(text: String) =
    text.mapIndexedNotNull { index, it ->
        if (it.isDigit()) {
            it
        } else {
            parseDigit(getSpelledDigitOrNull(text, index))
        }
    }.last()

fun main() {
    part1()
    part2()
}

fun part1() {
    getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day1\\input.txt").sumOf {
        "${it.first { it.isDigit() }}${it.reversed().first { it.isDigit() }}".toInt()
    }.let { println(it) }
}

fun part2() {
    getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day1\\input.txt").sumOf {
        "${getFirstDigit(it)}${getLastDigit(it)}".toInt()
    }.let { println(it) }
}