package com.github.marenwynn.torchsnipe;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ShotArrow {

    private Location startingLoc;
    private Vector   startingVelocity;

    public ShotArrow(Location startingLoc, Vector startingVelocity) {
        this.startingLoc = startingLoc;
        this.startingVelocity = startingVelocity;
    }

    public Location getStartingLoc() {
        return startingLoc;
    }

    public Vector getStartingVelocity() {
        return startingVelocity;
    }

}
