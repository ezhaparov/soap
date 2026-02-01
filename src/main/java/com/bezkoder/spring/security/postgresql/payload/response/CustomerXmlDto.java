package com.bezkoder.spring.security.postgresql.payload.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = {
    "idCustomer",
    "firstName",
    "lastName",
    "phone",
    "email"
})
public class CustomerXmlDto {

    @XmlElement(name = "id_customer")
    private UUID idCustomer;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    private String phone;
    private String email;

    public CustomerXmlDto() {
    }

    public CustomerXmlDto(UUID idCustomer, String firstName, String lastName, String phone, String email) {
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
