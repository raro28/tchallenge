package mx.ekthor.challenge.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converters {
    private static final ObjectMapper OM;

    static {
        OM = new ObjectMapper();
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private Converters() {
    }

    public static <E, M> E toEntity(M model, Class<E> _class) {
        return OM.convertValue(model, _class);
    }
}