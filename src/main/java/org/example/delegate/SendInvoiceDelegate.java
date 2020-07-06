package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

//TODO set invoice ID, WBIG address

public class SendInvoiceDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        String wbig_process_id = (String) delegateExecution.getVariable("wbig_process_id");
        String time_stamp = (String) delegateExecution.getVariable("time_stamp");
        String job_opening = (String) delegateExecution.getVariable("job_opening");
        String openingid = (String) delegateExecution.getVariable("openingid");
        Integer payment_info = (Integer) delegateExecution.getVariable("payment_info");
        Integer net = (Integer) delegateExecution.getVariable("net");
        Double gross = (Double) delegateExecution.getVariable("gross");
        Double tax = (Double) delegateExecution.getVariable("tax");
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        String openingName = (String) delegateExecution.getVariable("openingName");
        String processID = (String) delegateExecution.getVariable("processID");

        String date = (String) delegateExecution.getVariable("date");

                String invoiceJSON = "{\"WPLACM_process_ID\" : \"" +processID+"\","
                + "\"WBIG_process_ID\" : \"" +wbig_process_id+ "\","
                + "\"timestamp\" : \"" +time_stamp+"\","
                + "\"Invoice_ID\" : \"ID\","
                + "\"Payment_information_acceptances\" : \"" +payment_info+ ","
                + "\"Date\" : \"" +date+ "\","
                + "\"Tax_id_WPLACM\" : \"AB123456\","
                + "\"" +client_name+"\" : {"
                    + "\"street\" : \"WBIG street 1\","
                    + "\"post code\" : \"12345\""
                    + "}"
                +"\"address WPLACM\" : {"
                    + "\"street\" : \"Leonardo-Campus 3\","
                    + "\"post code\" : \"48149\""
                    + "}"
                +"\"Number_of_acceptances\" : \"" +number_of_acceptances+ "\","
                +"\"Opening_ID\" : \"" +openingid+ "\","
                +"\"Opening_Name\" : \"" +openingName+"\","
                +"\"Gross\" : \"" +gross+ "\","
                +"\"Net\" : \"" +net+ "\","
                +"\"Tax\" : \"" +tax+ "\"}";
        delegateExecution.setVariable("number_of_dunns", 0);
        //TODO: Message/JSON/POST see https://www.youtube.com/watch?v=8SYEc3dHnM4
        System.out.print(invoiceJSON);
        delegateExecution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("invoice_message") //TODO Message name
                .setVariable("invoice", invoiceJSON)
                .correlate();
    }

}
