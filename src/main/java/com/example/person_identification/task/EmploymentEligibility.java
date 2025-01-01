package com.example.person_identification.task;

import com.common.Person;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.EventEnvelope;
import org.platformlambda.core.models.TypedLambdaFunction;
import org.platformlambda.core.system.EventEmitter;
import org.platformlambda.core.system.PostOffice;
import org.platformlambda.core.util.Utility;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@PreLoad(route = "get.person", instances = 100, isPrivate = false)
public class EmploymentEligibility implements TypedLambdaFunction<Person, Person> {
    @Override
    public Person handleEvent(Map<String, String> headers, Person input, int instance) throws Exception {

        String traceId = Utility.getInstance().getUuid();
        PostOffice po = new PostOffice("person.test.endpoint", traceId, "GET /api/profile");
        EventEmitter eventemitter = EventEmitter.getInstance();
        EventEnvelope req1= new EventEnvelope().setTo("person.pojo");
        String remoteEndpoint = "http://127.0.0.1:8100/api/event";
        Person per = null;
        try {
            EventEnvelope response = po.request(req1, 3000, Collections.emptyMap(), remoteEndpoint, true).get();
//                if (response.getBody() instanceof Person result) {
            if (Person.class.getName().equals(response.getType())) {
                per = response.getBody(Person.class);
//                callback.success(per);
                EventEnvelope req2 = new EventEnvelope().setTo("employment.eligibility").setBody(per);
                eventemitter.send(req2);
                System.out.println("Fetch person data");
            } else {
                System.out.println("Error");
//                callback.error(new AppException(response.getStatus(), response.getError()));
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
//            callback.error(e);
            e.getMessage().toString();
        }

        return per;
    }
}
