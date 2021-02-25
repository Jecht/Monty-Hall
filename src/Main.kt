import controller.GameController
import view.View

fun main() {
    val controller = GameController(View())

    val userChangingDoorList = mutableListOf<Boolean>()
    val userNotChangingDoorList = mutableListOf<Boolean>()

    for (index in 1..100000000) {
        userChangingDoorList.add(controller.launchGame(userDidChangeHisChoice = true))
        userNotChangingDoorList.add(controller.launchGame(userDidChangeHisChoice = false))
    }

    println("Avec changement de de porte")
    println("Taux de succès : ${(userChangingDoorList.count { it }.toFloat() / userChangingDoorList.size.toFloat()) * 100}")
    println("Taux d'échecs : ${(userChangingDoorList.count { it.not() }.toFloat() / userChangingDoorList.size.toFloat()) * 100}")
    println()
    println("###########################################")
    println()
    println("Sans changement de de porte")
    println("Taux de succès : ${(userNotChangingDoorList.count { it }.toFloat() / userChangingDoorList.size.toFloat()) * 100}")
    println("Taux d'échecs : ${(userNotChangingDoorList.count { it.not() }.toFloat() / userChangingDoorList.size.toFloat()) * 100}")
}