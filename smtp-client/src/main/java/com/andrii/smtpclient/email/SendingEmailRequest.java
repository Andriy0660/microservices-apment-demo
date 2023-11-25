package com.andrii.smtpclient.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingEmailRequest {
    String message;
    String email;
    String subject;
}
