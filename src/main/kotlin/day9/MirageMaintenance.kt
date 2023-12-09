package day9

import getLinesFromFile
import java.util.*

fun main() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day9\\input.txt")
    part1(lines)
    println()
    part2(lines)
}

fun extrapolateForwards(history: List<Int>): Int {
    val stack = getHistoryDiffStack(history)

    var toAdd = 0
    while (stack.size > 1) {
        toAdd += stack.pop().last()
    }

    return stack.pop().last() + toAdd
}

fun extrapolateBackwards(history: List<Int>): Int {
    val stack = getHistoryDiffStack(history)

    var toSubtract = 0
    while (stack.size > 1) {
        toSubtract = stack.pop().first() - toSubtract
    }

    return stack.pop().first() - toSubtract
}

fun getHistoryDiffStack(history: List<Int>): Stack<List<Int>> {
    tailrec fun calculateDiff(currentDiff: List<Int>, accStack: Stack<List<Int>>): Stack<List<Int>> {
        return if (currentDiff.all { it == 0 }) {
            accStack
        } else {
            calculateDiff(
                currentDiff.windowed(2).map { ints -> ints[1] - ints[0] },
                accStack.apply { push(currentDiff) }
            )
        }
    }

    return calculateDiff(history, Stack<List<Int>>())
}

fun part1(lines: List<String>) {
    lines
        .map { it.split("\\s+".toRegex()).map { it.toInt() } }
        .sumOf { extrapolateForwards(it) }
        .let { print(it) }
}

fun part2(lines: List<String>) {
    lines
        .map { it.split("\\s+".toRegex()).map { it.toInt() } }
        .sumOf { extrapolateBackwards(it) }
        .let { print(it) }
}