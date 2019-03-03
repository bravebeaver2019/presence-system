package com.example.presence.processing;

import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AccessProcessorFactory {

    @Autowired
    @Qualifier("login")
    AccessProcessor login;

    @Autowired
    @Qualifier("logout")
    AccessProcessor logout;

    public AccessProcessor getAccessProcessor(FingerprintScan scan) {

        if (scan.getAccess().equals(Access.LOGIN)) {
            return login;
        }
        if (scan.getAccess().equals(Access.LOGOUT)) {
            return logout;
        } else {
            // todo: improve error handling
            throw new RuntimeException("No processor could be found for access type " + scan.getAccess());
        }

    }

}
