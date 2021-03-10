package mx.ekthor.challenge.rest.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.ekthor.challenge.rest.models.Player;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPlayer {
    @Builder.Default
    private List<Player> players = new ArrayList<>(1);
}
