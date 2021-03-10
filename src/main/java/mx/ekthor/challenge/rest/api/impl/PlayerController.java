package mx.ekthor.challenge.rest.api.impl;

import mx.ekthor.challenge.business.PlayerService;
import mx.ekthor.challenge.rest.api.PlayerAPI;
import mx.ekthor.challenge.rest.models.requests.PostPlayer;
import mx.ekthor.challenge.rest.models.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController implements PlayerAPI {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public ResponseEntity<Result> process(PostPlayer rq) {
        List<String> result = playerService.process(rq.getPlayers());
        return ResponseEntity.ok(Result.builder().result(result).build());
    }
}