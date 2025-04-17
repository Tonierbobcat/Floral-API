package com.loficostudios.floralcraftapi.character.entity;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.character.CharacterInstance;
import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.player.EntityPartyPlayer;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CharacterEntity extends EntityPartyPlayer {

    private final CharacterInstance instance;
    private final ActiveMob mythic;

    protected CharacterEntity(CharacterInstance instance, ActiveMob mythic) {
        super(mythic.getEntity().getBukkitEntity());
        this.mythic = mythic;
        this.instance = instance;

        FloralCraftAPI.inst().getCharacterEntityManager().addEntity(this);
    }

    public CharacterInstance getCharacterInstance() {
        return instance;
    }

    /**
     * Sets the level of the mob associated with this character.
     * This method applies only to the mob and not the character itself.
     * @deprecated Use {@link CharacterInstance#setLevel(int)} instead to set the level.
     */
    @Deprecated
    public void setLevel(int level) {
        mythic.setLevel(level);
    }

    @Override
    public @NotNull String getName() {
        return instance.getCharacter().getName();
    }

    @Override
    public int getLevel() {
        return (int) Math.round(mythic.getLevel());
    }

    @Override
    public @Nullable Party setCurrentParty(@Nullable Party party) {
        if (party != null) {
            mythic.setFaction(party.getUniqueId().toString());
        } else {
            mythic.setFaction(null);
        }
        return super.setCurrentParty(party);
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        mythic.getEntity().teleport(BukkitAdapter.adapt(location));
        return true;
    }

    public static CharacterEntity create(CharacterInstance instance, Location location) {
        return new CharacterEntity(instance, FloralCraftAPI.inst().getCharacterEntityManager().spawn(
                instance,
                location));
    }
}
