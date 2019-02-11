package uk.gov.ons.collection.businesslayer.controller

import java.util.Iterator

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._
import org.springframework.web.client.RestTemplate
import uk.gov.ons.collection.businesslayer.entity.Person
import uk.gov.ons.collection.businesslayer.service.PersonService

import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSON

@RestController
@RequestMapping(path = Array("/Person"))
class PersonController(@Autowired val personService: PersonService,
                      @Autowired mapper:ObjectMapper) {

  def business: String = return "<ul>" +
                          "<li><a href='/Person/returnAll'>Get all persons</a></li>" +
                          "<li><a href='/Person/FindPersonByFirstName'>Find persons by name</a></li>"

  @GetMapping(path = Array("/") ,produces = Array(MediaType.TEXT_HTML_VALUE))
  def home(): String = {
    business
  }

  @GetMapping(path = Array("/help"),produces = Array(MediaType.TEXT_HTML_VALUE))
  def help(): String = {
    business
  }

  @GetMapping(path = Array("returnAll"),produces = Array(MediaType.TEXT_HTML_VALUE))
  def printJSON():String = {
    val restTemplate = new RestTemplate
    restTemplate.getForObject("http://localhost:8080/doodles/findAll", classOf[String])
  }

  @GetMapping(path = Array("/FindPersonByFirstName/{firstName}"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def findByFirstNameLikeJSON(@PathVariable firstName:String):Iterable[Person] = {
    personService.findByFirstNameLikeJSON(firstName)
  }
}