package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()

  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

  // En las funciones declaradas con = no es necesario explicitar el tipo
  fun esVersatil() =
    certificaciones.size >= 3
      && this.certificacionesDeProducto() >= 1
      && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }

  fun esFirme() = this.puntajeCertificaciones() >= 30

  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }
  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  abstract fun influyente() : Boolean

  fun sumarCertificaciones(): Int {
    return certificaciones.sumBy({c -> c.puntaje })
  }
  fun certificadoNoPorProducto(): Boolean {
    return certificaciones.any({ c ->  !c.esDeProducto })
  }

  fun firme(): Boolean{
    return certificaciones.sumBy({c -> c.puntaje}) >= 30
  }

}

// En los parámetros, es obligatorio poner el tipo
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudad == ciudadOrigen
  }

  override fun influyente(): Boolean {
    return false
  }

}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }
  override fun influyente(): Boolean {
      return this.sumaPoblacion() >= 10000000
  }
  fun sumaPoblacion(): Int {
    return provinciasHabilitadas.sumBy({ p -> p.poblacion})
  }
}

class ComercioCorresponsal(val ciudades: Ciudad) : Vendedor() {

  fun agregarSucursalesEn(ciudad: Ciudad){
    ciudades.add(ciudad)
  }

  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }
  override fun influyente(): Boolean {
    return ciudades.size >= 5 || ciudades.map({ c->c.provincia }).toSet().size >= 3
  }
}
