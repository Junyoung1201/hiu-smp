package me.saehyeon.hiusmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
    public static boolean isWithin(Location target, Location pos1, Location pos2) {
        if(pos1 == null || pos2 == null) {
            return false;
        }

        if (!target.getWorld().equals(pos1.getWorld()) || !target.getWorld().equals(pos2.getWorld())) {
            return false;
        }

        // Get the min and max coordinates for X, Y, and Z
        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        // Check if the point's coordinates fall within all three ranges
        boolean withinX = target.getX() >= minX && target.getX() <= maxX;
        boolean withinY = target.getY() >= minY && target.getY() <= maxY;
        boolean withinZ = target.getZ() >= minZ && target.getZ() <= maxZ;

        return withinX && withinY && withinZ;
    }
}
