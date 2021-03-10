package mx.ekthor.challenge.business;

import mx.ekthor.challenge.rest.models.Player;

import java.util.List;

public interface PlayerService {
    List<String> process(List<Player> players);
}
