package net.dakotapride.incantation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

@Config(name = "incantation")
public class IncantationConfig implements ConfigData {

    public static boolean showTotemOfSalvationRemainingUses;
    public int enchantedBerryBushSpawnChance = 16;

    @Override
    public void validatePostLoad() {
        enchantedBerryBushSpawnChance = Math.max(enchantedBerryBushSpawnChance, 1);
    }
}
