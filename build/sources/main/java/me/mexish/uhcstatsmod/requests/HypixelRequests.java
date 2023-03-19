package me.mexish.uhcstatsmod.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;

import java.util.UUID;


@UtilityClass
public class HypixelRequests {

    // Hypixel API instance
    @Getter
    private static HypixelAPI hypixelAPI;

    /**
     * Updates/sets the new hypixel API key.
     *
     * @param key   new API key
     */
    public void setKey(final @NonNull UUID key) {
        hypixelAPI = new HypixelAPI(new ApacheHttpClient(key));
    }

}
