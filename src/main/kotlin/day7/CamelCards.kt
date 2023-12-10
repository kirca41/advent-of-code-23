package day7

import getLinesFromFile

enum class Type {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD
}

fun main() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day7\\input.txt")

    part1(lines)
    println()
    part2(lines)
}

fun part1(lines: List<String>) {
    val cardOrder = mapOf(
        'A' to 0,
        'K' to 1,
        'Q' to 2,
        'J' to 3,
        'T' to 4,
        '9' to 5,
        '8' to 6,
        '7' to 7,
        '6' to 8,
        '5' to 9,
        '4' to 10,
        '3' to 11,
        '2' to 12
    )

    lines.associate {
        it.split("\\s+".toRegex())[0] to it.split("\\s+".toRegex())[1].toInt()
    }.toSortedMap { h1, h2 ->
        val strength1 = getHandStrength(h1)
        val strength2 = getHandStrength(h2)
        if (strength1 != strength2) {
            strength2.ordinal - strength1.ordinal
        } else {
            breakTie(h1, h2, cardOrder)
        }
    }
        .values
        .mapIndexed { index, v -> (index + 1) * v }
        .sum().let { print(it) }
}

fun part2(lines: List<String>) {
    val cardOrder = mapOf(
        'A' to 0,
        'K' to 1,
        'Q' to 2,
        'T' to 3,
        '9' to 4,
        '8' to 5,
        '7' to 6,
        '6' to 7,
        '5' to 8,
        '4' to 9,
        '3' to 10,
        '2' to 11,
        'J' to 12
    )

    lines.associate {
        it.split("\\s+".toRegex())[0] to it.split("\\s+".toRegex())[1].toInt()
    }.toSortedMap { h1, h2 ->
        val strength1 = getHandStrength(h1, true)
        val strength2 = getHandStrength(h2, true)
        if (strength1 != strength2) {
            strength2.ordinal - strength1.ordinal
        } else {
            breakTie(h1, h2, cardOrder)
        }
    }
        .values
        .mapIndexed { index, v -> (index + 1) * v }
        .sum().let { print(it) }
}

fun getHandStrength(hand: String, isPartTwo: Boolean = false): Type {
    val map = hand.toCharArray().associateWith { hand.count { c -> c == it } }.toMutableMap()

    if (isPartTwo && map.containsKey('J') && map.size > 1) {
        map.entries.filter { it.key != 'J' }
            .maxByOrNull { it.value }!!
            .also { (k, v) -> map[k] = v + hand.count { it == 'J' } }
        map.remove('J')
    }

    return when {
        map.containsValue(5) -> Type.FIVE_OF_A_KIND
        map.containsValue(4) -> Type.FOUR_OF_A_KIND
        map.containsValue(3) && map.containsValue(2) -> Type.FULL_HOUSE
        map.containsValue(3) -> Type.THREE_OF_A_KIND
        map.values.count { it == 2 } == 2 -> Type.TWO_PAIR
        map.containsValue(2) -> Type.ONE_PAIR
        else -> Type.HIGH_CARD
    }
}

fun breakTie(hand1: String, hand2: String, cardOrder: Map<Char, Int>): Int {

    hand1.zip(hand2).forEach { (c1, c2) ->
        if (c1 != c2) {
            return cardOrder[c2]!! - cardOrder[c1]!!
        }
    }

    return 0
}