package com.adrip.ce.exceptions;

import java.io.IOException;

public class ModifiableParameterException extends IOException {

    private static final long serialVersionUID = 1L;

    public ModifiableParameterException(String message) {
        super(message);
    }

}
