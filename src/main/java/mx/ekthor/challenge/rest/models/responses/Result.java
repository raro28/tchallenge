package mx.ekthor.challenge.rest.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Result {
    @Builder.Default
    private List<String> result = new ArrayList<>(1);
}
