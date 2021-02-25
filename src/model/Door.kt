package model

class Door(val id: Int, val isWinning: Boolean) {
    override fun equals(other: Any?) = if (other is Door) id == other.id else false
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + isWinning.hashCode()
        return result
    }
}