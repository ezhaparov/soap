package com.bezkoder.spring.security.postgresql.payload.response;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.Test;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JaxbSerializationTest {

    @Test
    public void testSerialization() throws Exception {
        CustomerXmlDto customer1 = new CustomerXmlDto(
                UUID.randomUUID(), "Kutman", "Sulaimanov", "123456789", "kutman@example.com"
        );
        CustomerXmlDto customer2 = new CustomerXmlDto(
                UUID.randomUUID(), "Erzhan", "Nurdavletov", "987654321", "erzhan@example.com"
        );

        CustomersXmlResponseJaxb response = new CustomersXmlResponseJaxb(List.of(customer1, customer2));

        JAXBContext context = JAXBContext.newInstance(CustomersXmlResponseJaxb.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(response, writer);
        String xml = writer.toString();

        System.out.println(xml);

        assertTrue(xml.contains("<customers_response>"));
        assertTrue(xml.contains("<customer>"));
        assertTrue(xml.contains("<id_customer>"));
        assertTrue(xml.contains("<first_name>John</first_name>"));
        assertTrue(xml.contains("<last_name>Doe</last_name>"));
        assertTrue(xml.contains("<email>jane.smith@example.com</email>"));
    }
}
