package com.location;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kie.api.definition.rule.Rule;

public class RuleRunner<T> {

    static final Logger LOG = LoggerFactory.getLogger(RuleRunner.class);

    public T run(T container){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        LOG.info("Creating kieBase");
        KieBase kieBase = kContainer.getKieBase("kb.com.location");

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }
        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();
        session.addEventListener(new TrackingAgendaEventListener());

        FactHandle factHandle = session.insert(container);
        try {
            int firedRules = session.fireAllRules();
            System.out.println("fired "+firedRules);
            return container;
        } finally {
            session.delete(factHandle);
            session.dispose();
        }
    }
}

