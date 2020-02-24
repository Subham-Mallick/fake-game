package com.psych.game.exceptions;

import javax.validation.constraints.NotBlank;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String message){
        super(message);
    }

}
