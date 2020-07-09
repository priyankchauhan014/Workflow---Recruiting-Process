package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.model.JobOpeningInformation;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Management")
public class ChangesDeclinedController {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(CandidatesPlacedController.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/ChangesDeclined/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueBillingProcess (@RequestBody Boolean dummy, @PathVariable ("id") String wplacm_processInstanceId) throws SQLException {


        //correlation specification via message name "CandidatesPlacedMessage". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("ChangesDeclined")
                //.processInstanceVariableEquals("WBIG_process_ID", wbig_processInstanceId)
                //.setVariable("wbig_processInstanceId", candidateInfo.getWBIG_processInstanceID())
                .processInstanceId(wplacm_processInstanceId)
                .correlate();

        System.out.println("Job Profile changes declined");
        return wplacm_processInstanceId;
    }

}
