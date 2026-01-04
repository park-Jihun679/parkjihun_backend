package org.wireBarley.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AccountCreateDTO {

    private String accountNo;

    private String accountName;

    private String bankCode;

}
