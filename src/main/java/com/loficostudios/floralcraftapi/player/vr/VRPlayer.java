package com.loficostudios.floralcraftapi.player.vr;

import com.cjcrafter.vivecraft.utils.Quaternion;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface VRPlayer {

    float getDraw();

    Vector getHMDDir();

    Quaternion getHMDRot();

    Vector getControllerDir(int controller);

    Quaternion getControllerRot(int controller);

    Location getHMDPos();

    Location getControllerPos(int c);

    boolean inVR();

    void setVR(boolean vr);

    boolean isSeated();

    byte[] getUberPacket();

    byte[] getVRPacket();

}
