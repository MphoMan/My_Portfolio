package io.jumpco.psetbackend.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ViolationInformation {
    private String invalidValue;
    private String message;
    private String propertyPath;

    public ViolationInformation(String invalidValue, String message, String propertyPath) {
        this.invalidValue = invalidValue;
        this.message = message;
        this.propertyPath = propertyPath;
    }

    public ViolationInformation() {
    }
}
