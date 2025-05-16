package org.example.mapper

import java.util.regex.Pattern
import org.example.dto.Interval
import org.example.dto.Step

object StepsMapper {
    private val patternWatts = Pattern.compile("(\\d+w)")
    private val patternTime = Pattern.compile("(\\d+.sec|(\\d:)?\\d+.min)")
    private val patternRepeat = Pattern.compile("(\\d+.X)")
    private val patternWarmCool = Pattern.compile("Warm-Up|Cooldown").toRegex()
    private val patternBpm = Pattern.compile("(\\d+-\\d+)")

    fun toSteps(instructions: List<String>) =
        mutableListOf<Step>().apply {
            instructions.forEach { i ->
                this.add(toStep(i))
            }
        }

    private fun toStep(step: String) =
        Step(
            intervals = toIntervals(step),
            repeat = toRepeat(step)?.replace("X", "")?.trim()?.toInt() ?: 1
        )

    private fun toIntervals(step: String): List<Interval> {
        val intervals = mutableListOf<Interval>()
        patternBpm.matcher(step).results().toList().forEachIndexed { idx, m ->
            val (bpmMin, bpmMax) = m.group().split("-").map { it.toInt() }
            intervals.add(
                Interval(
                    bpmMin = bpmMin,
                    bpmMax = bpmMax,
                    time = toTime(step, idx)
                )
            )
        }

        return intervals
    }

    private fun convertWarmCool(step: String) = step.replace(patternWarmCool, "(50w)")

    private fun toRepeat(step: String) = patternRepeat.matcher(step).results().toList().firstOrNull()?.group()

    private fun toTime(step: String, count: Int) = patternTime.matcher(step).results().toList()[count].group()

}