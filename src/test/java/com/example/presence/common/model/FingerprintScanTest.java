package com.example.presence.common.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Test json annotations are properly setup.
 */
public class FingerprintScanTest {

    SimpleDateFormat simpleDateFormat;
    final ObjectMapper mapper = new ObjectMapper();

    @Before public void setup() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);
    }

    @Test public void fingerprintScanDeserializerTest() throws Exception {

        final String jsonAsString = "{\"fingerprintHash\":\"4d8276c6732e92fd37fe6a3f9f58284a\"," +
                "\"scanTimestamp\":\"2011-11-02T02:50:12.208Z\", \"access\":\"LOGIN\"}";

        final FingerprintScan scan = mapper.readValue(jsonAsString, FingerprintScan.class);

        assertNotNull(scan);
        assertThat(scan.getFingerprintHash(), equalTo("4d8276c6732e92fd37fe6a3f9f58284a"));
        assertThat(scan.getAccess(), equalTo(Access.LOGIN));
        assertThat(scan.getScanTimestamp(), equalTo(
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z")));
    }

    @Test public void fingerprintScanSerializerTest() throws Exception {
        FingerprintScan scan = new FingerprintScan("4d8276c6732e92fd37fe6a3f9f58284a",
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN);
        final String scanJson = mapper.writeValueAsString(scan);

        assertNotNull(scanJson);
        assertEquals(scanJson, "{\"fingerprintHash\":\"4d8276c6732e92fd37fe6a3f9f58284a\",\"scanTimestamp\":\"2011-11-02T02:50:12.208Z\",\"access\":\"LOGIN\"}");
    }
}
