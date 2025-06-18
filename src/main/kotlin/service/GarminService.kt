package org.example.service

import org.example.dto.Step
import org.example.mapper.StepsMapper
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.Select


class GarminService {

    fun start(instructions: List<String>, name: String) {

        val driver = ChromeDriver(ChromeOptions().setExperimentalOption("debuggerAddress", "127.0.0.1:9222"))
        val steps = StepsMapper.toSteps(instructions)
        driver.get("https://connect.garmin.com/modern/workout/create/cycling")
        Thread.sleep(2000)

        clearInitialScreen(driver)

        steps.forEach { step: Step ->
            createStep(step, driver)
        }

        driver.findElement(By.className("icon-pencil")).click()
        Thread.sleep(200)
        driver.findElement(By.className("WorkoutName_workoutName__E7sq8")).sendKeys(name)
        Thread.sleep(200)
        driver.findElement(By.className("icon-checkmark")).click()
        Thread.sleep(200)
        driver.findElement(By.className("Button_primary__7zt4j")).click()
        Thread.sleep(2000)
        driver.findElement(By.className("Button_primary__7zt4j")).click()
    }

    private fun clearInitialScreen(driver: ChromeDriver) {
        val btnDelete = driver.findElements(By.className("icon-trash"))
        btnDelete.forEach {
            it.click()
            Thread.sleep(200)
            driver.findElement(By.className("Button_danger__hwLq8")).click()
        }
    }

    private fun createStep(step: Step, driver: WebDriver) {
        var first = (step.repeat > 1)
        var divRepeat: WebElement

        step.intervals.forEach {
            if (step.repeat > 1) {
                if (first) {
                    driver.findElements(By.className("Button_small__waifo")).last().click()
                    divRepeat= driver.findElements(By.className("WorkoutRepeatStep_repeatStepWrapper__s9T1j")).last()
                    val inputTimes: WebElement = divRepeat.findElement(By.className("FlexInput_input__FhFbe"))
                    inputTimes.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE))
                    inputTimes.sendKeys(step.repeat.toString())
                    divRepeat.findElements(By.className("WorkoutStep_stepFieldsWrapper__Dmd26")).first().click()
                    Thread.sleep(200)
                    first = false
                } else {
                    divRepeat = driver.findElements(By.className("WorkoutRepeatStep_repeatStepWrapper__s9T1j")).last()
                    divRepeat.findElements(By.className("WorkoutStep_stepFieldsWrapper__Dmd26")).last().click()
                    Select(divRepeat.findElement(By.id("select-details"))).selectByValue("interval")
                    Select(divRepeat.findElement(By.id("select-duration-rest-options"))).selectByValue("time")
                    Thread.sleep(200)
                }
            } else {
                driver.findElements(By.className("Button_small__waifo")).first().click()
                Thread.sleep(200)
                driver.findElements(By.className("WorkoutStep_stepReadOnlyWrapper__u3DgD")).last().click()
            }
            Thread.sleep(200)

            Select(driver.findElement(By.id("select-primary-target"))).selectByValue("heart.rate.zone")
            Thread.sleep(200)
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

            Thread.sleep(200)
            val hrInput: WebElement = driver.findElement(By.id("target-hr-input"))
            val hrTo: WebElement = driver.findElement(By.id("target-hr-to"))
            Thread.sleep(200)
            val actions = Actions(driver)
            actions.click(hrTo)
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(it.bpmMax.toString())
                .sendKeys(Keys.RETURN)
                .perform()
            actions.click(hrInput)
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(it.bpmMin.toString())
                .sendKeys(Keys.RETURN)
                .perform()
            actions.click(hrTo)
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(it.bpmMax.toString())
                .sendKeys(Keys.RETURN)
                .perform()
            actions.click(hrInput)
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .sendKeys(it.bpmMin.toString())
                .sendKeys(Keys.RETURN)
                .perform()
            driver.findElement(By.className("WorkoutStep_doneButton__xJZOm")).click()
        }
    }
}