package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Pegasus on 02/01/2017.
 */
public class EmailSend {

    private String receiptId;

    private Collection<String> email;

    public Collection<String> getEmail() {
        if(email == null) {
            email = new ArrayList<>();
        }
        return email;
    }

    public void setEmail(Collection<String> email) {
        this.email = email;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public String toString() {
        return "EmailSend{" +
                "receiptId=" + receiptId +
                ", email='" + email + '\'' +
                '}';
    }
}
