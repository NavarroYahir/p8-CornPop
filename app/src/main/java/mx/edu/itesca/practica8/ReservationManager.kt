package mx.edu.itesca.practica8

object ReservationManager {
    private val reservations = mutableMapOf<String, MutableSet<Int>>()

    fun getReservations(movie: String): Set<Int> {
        return reservations[movie] ?: emptySet()
    }

    fun reserveSeat(movie: String, seat: Int) {
        val set = reservations.getOrPut(movie) { mutableSetOf() }
        set.add(seat)
    }
}
