package com.loficostudios.floralcraftapi.player.vr;

import com.cjcrafter.vivecraft.VSE;
import com.cjcrafter.vivecraft.VivePlayer;
import com.cjcrafter.vivecraft.utils.Quaternion;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FloralPlayerVSE extends FloralPlayer implements VRPlayer {

    private final VivePlayer vr;

    /**
     *
     * @throws IllegalArgumentException if the player is not in VR
     */
    public FloralPlayerVSE(Player player) {
        super(validate(player));
        this.vr = VSE.vivePlayers.get(player.getUniqueId());
    }

    private static Player validate(Player player) {
        var vr = VSE.vivePlayers.get(player.getUniqueId());
        Validate.isTrue(vr == null || !vr.isVR(), "Player is not a VR player!");
        return player;
    }

    @Override
    public float getDraw() {
        return vr.getDraw();
    }

    @Override
    public Vector getHMDDir() {
        return vr.getHMDDir();
    }

    @Override
    public Quaternion getHMDRot() {
        return vr.getHMDRot();
    }

    @Override
    public Vector getControllerDir(int controller) {
        return vr.getControllerDir(controller);
    }

    @Override
    public Quaternion getControllerRot(int controller) {
        return vr.getControllerRot(controller);
    }

    @Override
    public Location getHMDPos() {
        return vr.getHMDPos();
    }

    @Override
    public Location getControllerPos(int c) {
        return vr.getControllerPos(c);
    }

    @Override
    public boolean inVR() {
        return vr.isVR();
    }

    @Override
    public void setVR(boolean vr) {
        this.vr.setVR(vr);
    }

    @Override
    public boolean isSeated() {
        return vr.isSeated();
    }

    @Override
    public byte[] getUberPacket() {
        return vr.getUberPacket();
    }

    @Override
    public byte[] getVRPacket() {
        return vr.getVRPacket();
    }
}
