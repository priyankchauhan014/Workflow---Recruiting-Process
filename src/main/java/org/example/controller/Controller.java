package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.model.NumberOfCandidates;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Billing")
public class Controller {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/Billing/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueBillingProcess (@RequestBody NumberOfCandidates candidateInfo, @PathVariable ("id") String wbig_processInstanceId) throws SQLException {

        LOGGER.info("Controller WBIG ProcessInstanceId: " + wbig_processInstanceId);

        //correlation specification via message name "SomeCVs". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("NumberOfCandidatesMessage")
                .processInstanceVariableEquals("wbig_processInstanceId", wbig_processInstanceId)
                .setVariable("number_of_acceptances", candidateInfo.getNumber_of_acceptances())
                .setVariable("payment_info", candidateInfo.getPayment_info())
                .correlate();


        LOGGER.info("WPLACM Billing started");
        return "Process started";
    }


}
