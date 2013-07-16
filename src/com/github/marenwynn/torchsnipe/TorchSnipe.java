package com.github.marenwynn.torchsnipe;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

public class TorchSnipe extends JavaPlugin implements Listener {

    HashMap<UUID, ShotArrow> arrows = new HashMap<UUID, ShotArrow>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Arrow))
            return;

        arrows.put(e.getEntity().getUniqueId(), new ShotArrow(e.getEntity().getLocation(), e.getEntity().getVelocity()));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!arrows.containsKey(e.getEntity().getUniqueId()))
            return;

        ShotArrow sa = arrows.get(e.getEntity().getUniqueId());
        BlockIterator bi = new BlockIterator(e.getEntity().getWorld(), sa.getStartingLoc().toVector(), sa.getStartingVelocity(), 0, (int) sa.getStartingLoc().distance(e.getEntity().getLocation()) + 1);

        while (bi.hasNext()) {
            Block b = bi.next();

            if (b.getType() == Material.TORCH || b.getType() == Material.REDSTONE_TORCH_OFF || b.getType() == Material.REDSTONE_TORCH_ON) {
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(b.getType(), 1));
                b.setType(Material.AIR);
            }
        }

        arrows.remove(e.getEntity().getUniqueId());
    }

}
