package controller

import changeSelection
import enterDoorNumber
import errorInputType
import errorWrongChoiceNumber
import errorWrongDoorNumber
import gameRulesExplanations
import loose
import model.Door
import thanks
import view.View
import welcomeMessage
import win
import wrongDoorToDisplay
import kotlin.random.Random

class GameController(private val view: View) {
    fun launchGame() {
        view.display(welcomeMessage)
        view.display(gameRulesExplanations)

        val doorList = createDoorList()

        val doorIndex = getDoorNumberSelectedByUser() - 1
        var selectedDoor = doorList[doorIndex]
        val wrongDoorToShow = doorList.filter { it.id != selectedDoor.id }.first { it.isWinning.not() }

        view.display(String.format(wrongDoorToDisplay, doorList.indexOf(wrongDoorToShow)))
        val userDidChangeHisChoice = doesUserChangeHisChoice()

        if (userDidChangeHisChoice) {
            selectedDoor = doorList.first { it != selectedDoor && it != wrongDoorToShow }
        }

        println(if (selectedDoor.isWinning) win else loose)
    }

    fun launchGame(userDidChangeHisChoice: Boolean): Boolean {
        val doorList = createDoorList()

        val doorIndex = Random.nextInt(0, 2)
        var selectedDoor = doorList[doorIndex]
        val wrongDoorToShow = doorList.filter { it.id != selectedDoor.id }.first { it.isWinning.not() }

        if (userDidChangeHisChoice) {
            selectedDoor = doorList.first { it != selectedDoor && it != wrongDoorToShow }
        }

        return selectedDoor.isWinning
    }

    private fun getDoorNumberSelectedByUser(): Int {
        view.display(enterDoorNumber)
        var doorNumber = -1
        while (isNumberAccurate(input = doorNumber).not()) {
            try {
                doorNumber = getUserInput()
                if (isNumberAccurate(input = doorNumber).not()) {
                    view.display(errorWrongDoorNumber)
                    view.display(enterDoorNumber)
                } else {
                    view.display(thanks)
                }
            } catch (e: Exception) {
                view.display(errorInputType)
                view.display(enterDoorNumber)
            }
        }
        return doorNumber
    }

    private fun doesUserChangeHisChoice(): Boolean {
        view.display(changeSelection)
        var userInput = -1
        while ((userInput in 0..1).not()) {
            try {
                userInput = getUserInput()
                if ((userInput in 0..1).not()) {
                    view.display(errorWrongChoiceNumber)
                    view.display(changeSelection)
                } else {
                    view.display(thanks)
                }
            } catch (e: Exception) {
                view.display(errorInputType)
                view.display(changeSelection)
            }
        }
        return userInput == 1
    }

    private fun createDoorList(): MutableList<Door> {
        val doorList = mutableListOf<Door>()

        val winningDoor = Door(id = Random.nextInt(), isWinning = true)
        doorList.add(winningDoor)
        for (i in 0..1) {
            val door = Door(id = Random.nextInt(), isWinning = false)
            doorList.add(door)
        }

        doorList.shuffle()

        return doorList
    }

    @Throws
    private fun getUserInput() = readLine()?.toInt() ?: -1

    private fun isNumberAccurate(input: Int) = input in 1..3

}