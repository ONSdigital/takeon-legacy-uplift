package uk.gov.ons.collection.businesslayer.service

import java.io.IOException
import java.util.ArrayList

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.json4s.{DefaultFormats, JArray}
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.{UriComponents, UriComponentsBuilder}
import uk.gov.ons.collection.businesslayer.entity.Person

import scala.collection.JavaConverters._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.immutable.StringLike

@Service
class PersonService (@Autowired mapper:ObjectMapper) {
  var PersistanceLayerIP = "localhost"
  var port = 8080

  /* now using the mapper:ObjectMapper bean from Config
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JavaTimeModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
  */

  def readNameNode(jsonString: String): Iterable[Person] = {
    //var firstnameNode:String = null
    //var surnameNode:String = null
    //var idNode:java.lang.Long = null
    //implicit val formats = Serialization.formats(NoTypeHints)
    implicit val formats = DefaultFormats

    val persons = new ArrayList[Person]

    /* using json4s library
    val json = parse(jsonString)
    val myJArray:JArray = json.extract[JArray]

    val jValues =myJArray.arr
    var person:Person = null
    for(i <- 0 until jValues.length) {
      val idNode: String = (jValues(i) \\ "nameID").extract[String]
      val firstnameNode: String = (jValues(i) \\ "firstName").extract[String]
      val surnameNode: String =(jValues(i) \\ "surName").extract[String]
      //println(s"${idNode} ${firstnameNode} ${surnameNode}")
      person = new Person(idNode, firstnameNode, surnameNode)
      persons.add(person)
    }

    serialize(persons)
    */

    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

    //val parsedJson:List[Map[String, String]] = mapper.readValue(jsonString,classOf[List[Map[String, String]]])
    val parsedJson:List[Map[String, Any]] = mapper.readValue(jsonString,new TypeReference[List[Map[String, Any]]]{})


    var person:Person = null
    for(i <- 0 until parsedJson.length) {

      //using StringLike to convert to Long
      //val idNode: Long = parsedJson(i).get("nameID").getOrElse("").toString.toLong
      val idNode: Long = parsedJson(i).get("nameID").getOrElse("").toString.toLong
      val firstnameNode: String = parsedJson(i).get("firstName").getOrElse("").asInstanceOf[String]
      val surnameNode: String =parsedJson(i).get("surName").getOrElse("").asInstanceOf[String]
      // the below is commenetd and was an example of using Map[String,String] instead of Any
      //val idNode: String = parsedJson(i).get("nameID").getOrElse("")
      //val firstnameNode: String = parsedJson(i).get("firstName").getOrElse("")
      //val surnameNode: String =parsedJson(i).get("surName") match {
        //case None => ""
        //case Some(value) => value
      //}

      person = new Person(idNode, firstnameNode, surnameNode)
      persons.add(person)
    }

    // now it can return the iterator
    // because we have the default http mapper in the config of Spring
    // asScala conversion is needed for the conversion of LisTArray to iterator type
    return persons.asScala

  }

  def serialize(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k,v) => k.name -> v})
  }

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def findByFirstNameLikeJSON(firstName: String): Iterable[Person] = {
    val uriComponents = UriComponentsBuilder.newInstance.scheme("http").host(PersistanceLayerIP).port(port).path("doodles").path("/findByFirstNameLike").path("/" + firstName).build
    val uri = uriComponents.toUriString
    val restTemplate = new RestTemplate
    val jsonOutput = restTemplate.getForObject(uri, classOf[String])

    try {
      return readNameNode(jsonOutput)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
    }

    return null
  }

}
