package uk.gov.ons.collection.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.ons.collection.entity.Person;

import java.io.IOException;
import java.util.ArrayList;


@Service
public class PersonService{

    private final String PersistanceLayerIP = "localhost";
    private final Integer port = 8080;

    private ArrayList<Person> readNameNode(String jsonString) throws IOException
    {
        ArrayList<Person> persons = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonString);
        if (rootNode.isArray()) {

            JsonNode firstnameNode;
            JsonNode surnameNode;
            JsonNode idNode;

            // Extract each json attribute and use it them to create our local business object
            for (final JsonNode objNode : rootNode) {
                surnameNode = objNode.path("surName");
                firstnameNode = objNode.path("firstName");
                idNode = objNode.path("nameID");
                Person person = new Person(idNode.asInt(), firstnameNode.asText(), surnameNode.asText());
                persons.add(person);
            }
        }
        return persons;
    }

    public Iterable findByFirstNameLikeJSON(String firstName) {

        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(PersistanceLayerIP).port(port)
                .path("doodles").path("/findByFirstNameLike").path("/" + firstName).build();
        String uri = uriComponents.toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String jsonOutput = restTemplate.getForObject(uri, String.class);
        try {
            return readNameNode(jsonOutput);
        } catch (IOException e) {
            // TODO: Fine for a hello world. Not good for proper implementation
        }
        return null; // TODO: This should return an empty iterable, not null
    }

}

