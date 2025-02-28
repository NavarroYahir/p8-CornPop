package mx.edu.itesca.practica8

data class Pelicula(var titulo:String,
                    var image:Int,
                    var header:Int,
                    var sinopsis:String,
                    val seats:ArrayList<Cliente>)
