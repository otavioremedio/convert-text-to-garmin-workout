package org.example.service

import org.example.dto.Step
import org.example.mapper.StepsMapper
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Select

class GarminService {

    fun start(instructions: List<String>, name: String) {

        val driver = ChromeDriver(ChromeOptions().setExperimentalOption("debuggerAddress", "127.0.0.1:9222"))
        val steps = StepsMapper.toSteps(instructions)

        clearInitialScreen(driver)

        steps.forEach { step: Step ->
            createStep(step, driver)
        }

        driver.findElement(By.className("icon-pencil")).click()
        Thread.sleep(500)
        driver.findElement(By.className("WorkoutName_workoutName__E7sq8")).sendKeys(name)
        Thread.sleep(500)
        driver.findElement(By.className("icon-checkmark")).click()
        Thread.sleep(500)
        driver.findElement(By.className("Button_btn__g8LLk")).click()
        Thread.sleep(2000)
        driver.findElement(By.className("Button_btn__g8LLk")).click()
    }

    private fun clearInitialScreen(driver: ChromeDriver) {
        val btnDelete = driver.findElements(By.className("icon-trash"))
        btnDelete.forEach {
            it.click()
            Thread.sleep(500)
            driver.findElement(By.className("Button_btnDanger__137s7")).click()
        }
    }

    private fun createStep(step: Step, driver: WebDriver) {
        var first = (step.repeat > 1)
        var divRepeat: WebElement

        step.intervals.forEach {
            if (step.repeat > 1) {
                if (first) {
                    driver.findElements(By.className("Button_btnSmall__J8IWB")).last().click()
                    divRepeat= driver.findElements(By.className("WorkoutRepeatStep_repeatStepWrapper__s9T1j")).last()
                    val inputTimes: WebElement = divRepeat.findElement(By.className("FlexInput_input__FhFbe"))
                    inputTimes.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE))
                    inputTimes.sendKeys(step.repeat.toString())
                    divRepeat.findElements(By.className("WorkoutStep_stepFieldsWrapper__Dmd26")).first().click()
                    Thread.sleep(500)
                    first = false
                } else {
                    divRepeat = driver.findElements(By.className("WorkoutRepeatStep_repeatStepWrapper__s9T1j")).last()
                    divRepeat.findElements(By.className("WorkoutStep_stepFieldsWrapper__Dmd26")).last().click()
                    Select(divRepeat.findElement(By.id("select-details"))).selectByValue("interval")
                    Select(divRepeat.findElement(By.id("select-duration-rest-options"))).selectByValue("time")
                    Thread.sleep(500)
                }
            } else {
                driver.findElements(By.className("Button_btnSmall__J8IWB")).first().click()
                Thread.sleep(500)
                driver.findElements(By.className("WorkoutStep_stepReadOnlyWrapper__u3DgD")).last().click()
            }
            Thread.sleep(500)

            Select(driver.findElement(By.id("select-primary-target"))).selectByValue("heart.rate.zone")
            Thread.sleep(500)
            if (it.time.contains("min")) {
                if(it.time.contains(":")) {
                    val timeSplited = it.time.split(":")
                    driver.findElement(By.className("TimeDurationInput_minutes__20akP")).sendKeys(timeSplited[0])
                    driver.findElement(By.className("TimeDurationInput_seconds__n9cXY")).sendKeys(timeSplited[1].filter{t -> t.isDigit() })
                } else {
                    driver.findElement(By.className("TimeDurationInput_minutes__20akP")).sendKeys(it.time.filter{t -> t.isDigit() })
                }
            } else {
                driver.findElement(By.className("TimeDurationInput_minutes__20akP"))
                    .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE))
                driver.findElement(By.className("TimeDurationInput_seconds__n9cXY")).sendKeys(it.time.filter{t -> t.isDigit() })
            }

            Thread.sleep(500)
            val hrTo: WebElement = driver.findElement(By.id("target-hr-to"))
            hrTo.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE))
            hrTo.sendKeys((it.bpmMax).toString())
            Thread.sleep(500)
            val hrInput: WebElement = driver.findElement(By.id("target-hr-input"))
            hrInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE))
            hrInput.sendKeys(it.bpmMin.toString())
            Thread.sleep(500)
            driver.findElement(By.className("WorkoutStep_doneButton__xJZOm")).click()
            Thread.sleep(500)
        }
    }
}