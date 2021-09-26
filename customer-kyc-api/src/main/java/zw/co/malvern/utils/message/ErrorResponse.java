package zw.co.malvern.utils.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ErrorResponse extends BasicResponse{
    private String errorMessage;
}
