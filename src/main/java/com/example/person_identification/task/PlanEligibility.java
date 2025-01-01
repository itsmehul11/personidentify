package com.example.person_identification.task;

import com.common.Plan;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.EventEnvelope;
import org.platformlambda.core.models.TypedLambdaFunction;
import org.platformlambda.core.system.EventEmitter;
import org.platformlambda.core.system.PostOffice;
import org.platformlambda.core.util.AppConfigReader;
import org.platformlambda.core.util.Utility;

import java.util.Map;

@PreLoad(route = "get.plan", instances = 100)
public class PlanEligibility implements TypedLambdaFunction<Plan, Plan> {
    @Override
    public Plan handleEvent(Map<String, String> headers, Plan input, int instance) throws Exception {

        Plan pl = new Plan();
        pl.setId(12345);
        pl.setType("Retirement");

        EventEmitter eventemitter = EventEmitter.getInstance();
        EventEnvelope req2 = new EventEnvelope().setTo("plan.eligibility").setBody(pl);
        eventemitter.send(req2);

        return pl;
    }
}
