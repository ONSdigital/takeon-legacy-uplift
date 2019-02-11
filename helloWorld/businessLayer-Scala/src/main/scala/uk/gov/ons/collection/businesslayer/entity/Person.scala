package uk.gov.ons.collection.businesslayer.entity


class Person(_id: Long, _firstname: String, _surname: String) {
  var id = _id
  var firstname =  _firstname
  var surname = _surname

  def formatName (name: String) : String = {
    if (name.length == 0) {
      return  name
    }
    return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase()
  }

  def stripAll(s: String, bad: String): String = {

    @scala.annotation.tailrec def start(n: Int): String =
      if (n == s.length) ""
      else if (bad.indexOf(s.charAt(n)) < 0) end(n, s.length)
      else start(1 + n)

    @scala.annotation.tailrec def end(a: Int, n: Int): String =
      if (n <= a) s.substring(a, n)
      else if (bad.indexOf(s.charAt(n - 1)) < 0) s.substring(a, n)
      else end(a, n - 1)

    start(0)
  }

  def getFormattedFirstName : String = {
    return formatName(stripAll(firstname.toString," ")).toLowerCase.capitalize
  }

  def getFormattedLastName : String = {
    return formatName(stripAll(surname.toString," ")).toLowerCase.capitalize
  }

  override def toString = {
    s"${id} ${firstname} ${surname}"
  }
}

