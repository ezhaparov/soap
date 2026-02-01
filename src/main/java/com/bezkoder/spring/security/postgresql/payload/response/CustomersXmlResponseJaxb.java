package com.bezkoder.spring.security.postgresql.payload.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers_response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersXmlResponseJaxb {

    @XmlElement(name = "customer")
    private List<CustomerXmlDto> customers = new ArrayList<>();

    public CustomersXmlResponseJaxb() {
    }

    public CustomersXmlResponseJaxb(List<CustomerXmlDto> customers) {
        this.customers = customers;
    }

    public List<CustomerXmlDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerXmlDto> customers) {
        this.customers = customers;
    }
}
