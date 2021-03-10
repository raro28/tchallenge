package mx.ekthor.challenge.business.impl;

import mx.ekthor.challenge.business.PlayerService;
import mx.ekthor.challenge.persistence.repositories.PlayerRepository;
import mx.ekthor.challenge.rest.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final KafkaTemplate<Object, Object> template;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, KafkaTemplate<Object, Object> template) {
        this.playerRepository = playerRepository;
        this.template = template;
    }

    @Override
    public List<String> process(List<Player> players) {
        return null;
    }
}
