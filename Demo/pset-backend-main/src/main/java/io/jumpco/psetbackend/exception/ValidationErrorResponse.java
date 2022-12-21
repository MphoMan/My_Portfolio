package io.jumpco.psetbackend.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ValidationErrorResponse {
    private Set<ViolationInformation> violationInformations = new HashSet<>();
    private HttpStatus httpStatus;
    private ZonedDateTime timeStamp;
}
