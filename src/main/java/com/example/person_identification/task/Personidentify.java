package com.example.person_identification.task;

import com.example.person_identification.models.Person;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;

import java.util.Map;
import java.util.Objects;
@PreLoad( route = "person.identify", instances = 100,isPrivate = false)
public class Personidentify implements TypedLambdaFunction<Person,Person> {
    @Override
    public Person handleEvent(Map<String, String> headers, Person input, int instance) throws Exception {

        System.out.println("person identify class");
        return null;
    }
}
