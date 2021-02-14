package mn.guren.gis.util;

import io.activej.codec.StructuredCodec;
import io.activej.codec.registry.CodecRegistry;

import java.time.LocalDate;

public final class Registry {
    //TODO complete registry here
    public static final CodecRegistry REGISTRY = CodecRegistry.create()
            .with(LocalDate.class, StructuredCodec.of(
                    in -> LocalDate.parse(in.readString()),
                    (out, item) -> out.writeString(item.toString())));
}