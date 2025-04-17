package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.config.Config;
import com.loficostudios.floralcraftapi.file.impl.YamlFile;
import com.loficostudios.floralcraftapi.mechanics.crafting.stations.anvil.UpgradeAnvil;
import com.loficostudios.floralcraftapi.nms.NMSProvider;
import com.loficostudios.floralcraftapi.npc.NPCProvider;
import com.loficostudios.floralcraftapi.profile.AbstractProfileManager;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.world.WorldProvider;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;

public class FloralCraftAPIConfig {
    private final AbstractProfileManager<ProfileData> profileManager;
//    private final RegionProvider regionProvider;
    private final WorldProvider worldProvider;
    private final NMSProvider nmsProvider;
    private final NPCProvider npcProvider;
    private final HashMap<String, Config> configs;

    private FloralCraftAPIConfig(Builder builder) {
        this.profileManager = builder.profileManager;
//        this.regionProvider = builder.regionProvider;
        this.worldProvider = builder.worldProvider;
        this.nmsProvider = builder.nmsProvider;
        this.npcProvider = builder.npcProvider;
        this.configs = new HashMap<>();
//        this.configs.put("claims", builder.claimsConfig);
        this.configs.put("attributes", builder.attributeConfig);
        this.configs.put("stats", builder.statsConfig);
        this.configs.put("player-leveling", builder.playerLevelingConfig);
        this.configs.put("character-leveling", builder.characterLevelingConfig);
        this.configs.put("upgrade-anvil", builder.upgradeAnvilConfig);
    }

//    public Config claims() {
//        return this.configs.get("claims");
//    }
    public Config stats() {
        return this.configs.get("stats");
    }
    public Config playerLeveling() {
        return this.configs.get("player-leveling");
    }
    public Config characterLeveling() {
        return this.configs.get("character-leveling");
    }
    public Config attributes() {
        return this.configs.get("attributes");
    }

    public AbstractProfileManager<ProfileData> getProfileManager() {
        return profileManager;
    }

//    public RegionProvider getRegionProvider() {
//        return regionProvider;
//    }

    public NPCProvider getNPCProvider() {
        return npcProvider;
    }

    public WorldProvider getWorldProvider() {
        return worldProvider;
    }

    public NMSProvider getNMSProvider() {
        return nmsProvider;
    }

    public Config upgradeAnvil() {
        return this.configs.get("upgrade-anvil");
    }

    public static class Builder {

        public NPCProvider npcProvider;
        private AbstractProfileManager<ProfileData> profileManager;
//        private RegionProvider regionProvider;
        private WorldProvider worldProvider;
        private NMSProvider nmsProvider;

        public Builder profileManager(AbstractProfileManager<ProfileData> profileManager) {
            this.profileManager = profileManager;
            return this;
        }

//        public Builder regionProvider(RegionProvider regionProvider) {
//            this.regionProvider = regionProvider;
//            return this;
//        }

        public Builder worldProvider(WorldProvider worldProvider) {
            this.worldProvider = worldProvider;
            return this;
        }

        public Builder npcProvider(NPCProvider npcProvider) {
            this.npcProvider = npcProvider;
            return this;
        }

        public Builder nmsProvider(NMSProvider nmsProvider) {
            this.nmsProvider = nmsProvider;
            return this;
        }

        private Config upgradeAnvilConfig;
//        private Config claimsConfig;
        private Config attributeConfig;
        private Config statsConfig;
        private Config playerLevelingConfig;
        private Config characterLevelingConfig;

//        public Builder claims(YamlFile file) {
//            this.claimsConfig = new Config(file);
//            return this;
//        }

        public Builder upgradeAnvil(HashMap<String, UpgradeAnvil.ResourceRequirement> requirementsPerType) {
            upgradeAnvilConfig = new Config.Builder()
                    .set("requirements-per-type", requirementsPerType)
                    .build();
            return this;
        }

        public Builder attributes(YamlFile file) {
            this.attributeConfig = new Config(file);
            return this;
        }

        public Builder stats(YamlFile file) {
            this.statsConfig = new Config(file);
            return this;
        }

        public Builder playerLeveling(YamlFile file) {
            this.playerLevelingConfig = new Config(file);
            return this;
        }

        public Builder characterLeveling(YamlFile file) {
            this.characterLevelingConfig = new Config(file);
            return this;
        }

        public FloralCraftAPIConfig build() {
            Validate.isTrue(profileManager != null);
//            Validate.isTrue(regionProvider != null);
//            Validate.isTrue(regionProvider != null);
            Validate.isTrue(worldProvider != null);
            Validate.isTrue(nmsProvider != null);
            Validate.isTrue(npcProvider != null);
            Validate.isTrue(upgradeAnvilConfig != null);
            Validate.isTrue(attributeConfig != null);
            Validate.isTrue(statsConfig != null);
            Validate.isTrue(playerLevelingConfig != null);
            Validate.isTrue(characterLevelingConfig != null);

            return new FloralCraftAPIConfig(this);
        }
    }
}
