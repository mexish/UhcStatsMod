package me.mexish.uhcstatsmod.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.common.config.Configuration;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Data
public class UhcConfiguration {

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    private final Configuration configuration;

    public UhcConfiguration(final @NonNull Configuration file) {
        this.configuration = file;
    }
    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    /** Hypixel API key */
    UUID apiKey;

    /** Indicates if we need to see debug output and shit */
    boolean isDebugging;
    String trackedPlayer;



    public @NonNull UhcConfiguration load() {
        configuration.load();
        apiKey = UUID.fromString(String.valueOf(configuration.get("general", "apiKey", "f96b9111-8de9-45ab-a5a0-1d70b394b4db").getString()));
        isDebugging = configuration.get("general", "debugging", true).getBoolean();
        trackedPlayer = configuration.get("tracker", "mexish", true).getString();
        configuration.save();
        return this;
    }

    public void save() {
        configuration.get("general", "debugging", true).set(isDebugging);
        configuration.get("general", "apiKey", "").set(apiKey.toString());
        configuration.get("tracker", "trackedPlayer", "").set(trackedPlayer);
        configuration.save();
    }
}
