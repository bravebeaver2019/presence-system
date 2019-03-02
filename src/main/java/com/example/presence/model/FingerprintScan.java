package com.example.presence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents the scan read of a fingerprint at a given moment.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FingerprintScan {

    /**
     * each employee fingerprint will have an unique hash.
     * It will allow us to identify the employee by this id.
     */
    String fingerprintHash;

    /**
     * we will consider all dates are in GMT.
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
    Date scanTimestamp;
}
