package mx.ekthor.challenge.business.impl;

import mx.ekthor.challenge.business.PlayerService;
import mx.ekthor.challenge.persistence.entities.PlayerEntity;
import mx.ekthor.challenge.persistence.repositories.PlayerRepository;
import mx.ekthor.challenge.rest.models.Player;
import mx.ekthor.challenge.utils.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final KafkaTemplate<Object, Object> template;

    @Value("${challenge.topic}")
    private String topic;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, KafkaTemplate<Object, Object> template) {
        this.playerRepository = playerRepository;
        this.template = template;
    }

    @Override
    public List<String> process(List<Player> players) {
        List<String> result = new ArrayList<>(players.size());
        List<PlayerEntity> dbPlayers = new ArrayList<>();

        for (Player player : players) {
            switch (player.getType()) {
                case "expert":
                    dbPlayers.add(Converters.toEntity(player, PlayerEntity.class));
                    result.add(String.format("player %s stored in DB", player.getName()));
                    break;
                case "novice":
                    template.send(topic, player);
                    result.add(String.format("player %s sent to Kafka topic", player.getName()));
                    break;
                default:
                    result.add(String.format("player %s did not fit", player.getName()));
                    break;
            }
        }

        playerRepository.saveAll(dbPlayers);

        return result;
    }
}
