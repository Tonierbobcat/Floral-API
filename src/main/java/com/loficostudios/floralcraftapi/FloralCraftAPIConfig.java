package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.config.Config;
import com.loficostudios.floralcraftapi.file.impl.YamlFile;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;

public class FloralCraftAPIConfig { //todo remove this
    private final HashMap<String, Config> configs;

    private FloralCraftAPIConfig(Builder builder) {
        this.configs = new HashMap<>();

        this.configs.put("attributes", builder.attributeConfig);
        this.configs.put("stats", builder.statsConfig);
        this.configs.put("player-leveling", builder.playerLevelingConfig);
        this.configs.put("character-leveling", builder.characterLevelingConfig);
    }


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

    public static class Builder {

        private Config attributeConfig;
        private Config statsConfig;
        private Config playerLevelingConfig;
        private Config characterLevelingConfig;

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
            Validate.isTrue(attributeConfig != null);
            Validate.isTrue(statsConfig != null);
            Validate.isTrue(playerLevelingConfig != null);
            Validate.isTrue(characterLevelingConfig != null);

            return new FloralCraftAPIConfig(this);
        }
    }
}
