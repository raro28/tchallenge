package mx.ekthor.challenge.business.impl;

import mx.ekthor.challenge.business.PlayerService;
import mx.ekthor.challenge.persistence.entities.PlayerEntity;
import mx.ekthor.challenge.persistence.repositories.PlayerRepository;
import mx.ekthor.challenge.rest.models.Player;
import mx.ekthor.challenge.utils.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final DirectChannel producerChannel;
    private final Map<String, String> headers;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, DirectChannel producerChannel, @Value("${challenge.topic}") String topic) {
        this.playerRepository = playerRepository;
        this.producerChannel = producerChannel;
        this.headers = Collections.singletonMap(KafkaHeaders.TOPIC, topic);
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
                    producerChannel.send(new GenericMessage (player, headers));
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
