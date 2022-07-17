package net.dakotapride.incantation.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "incantation")
public class IncantationConfig implements ConfigData {

    public int enchantedBerryBushSpawnChance = 16;

    @Override
    public void validatePostLoad() {
        enchantedBerryBushSpawnChance = Math.max(enchantedBerryBushSpawnChance, 1);
    }
}
