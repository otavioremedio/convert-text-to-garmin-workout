package org.example.mapper

import org.example.service.Converter
import org.example.dto.Interval
import org.example.dto.Step
import java.util.regex.Pattern

object StepsMapper {
    private val patternWatts: Pattern = Pattern.compile("(\\d+w)")
    private val patternTime: Pattern = Pattern.compile("(\\d+.sec|\\d+.min)")
    private val patternRepeat: Pattern = Pattern.compile("(\\d+.X)")

    fun toSteps(instructions: List<String>, lthr: Int) =
        mutableListOf<Step>().apply {
            instructions.forEach { i ->
                this.add(toStep(i, lthr))
            }
        }

    private fun toStep(step: String, lthr: Int) =
        Step(
            intervals = toIntervals(step, lthr),
            repeat = toRepeat(step)?.replace("X", "")?.trim()?.toInt() ?: 1
        )

    private fun toIntervals(step: String, lthr: Int): List<Interval> {
        val intervals = mutableListOf<Interval>()
        patternWatts.matcher(step).results().toList().forEachIndexed { idx, m ->
            val ftp = m.group().replace("w","").toInt()
            intervals.add(
                Interval(
                    ftp = ftp,
                    bpm = (lthr * (Converter.mapTable[ftp]!! / 100)).toInt(),
                    time = toTime(step, idx)
                )
            )
        }

        return intervals
    }

    private fun toRepeat(step: String) = patternRepeat.matcher(step).results().toList().firstOrNull()?.group()

    private fun toTime(step: String, count: Int) = patternTime.matcher(step).results().toList()[count].group()

}