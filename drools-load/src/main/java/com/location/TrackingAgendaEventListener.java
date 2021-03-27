package com.location;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** Used to track which rules were actually fired during the execution of the rules. */
public class TrackingAgendaEventListener extends DefaultAgendaEventListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();
        printOut(rule, "Matched rule: {}");
    }

    private void printOut(Rule rule, String message) {
        String ruleName = rule.getName();
        log.info(message, ruleName);
    }
}
