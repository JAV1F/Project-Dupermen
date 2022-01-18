package com.ja.dupermen.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class PlayerUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos getPlayerPos(EntityPlayer player) {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }

    public static BlockPos getBlock(Block block, double range) {
        BlockPos pos = null;
        double distance = range;
        for (BlockPos blockPos : BlockUtils.getSphere(PlayerUtils.getPlayerPos(mc.player), (float) range, (int) range,false, true, 0)) {
            double bDistance = mc.player.getDistance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
            if (bDistance >= distance || mc.world.getBlockState(blockPos).getBlock() != block) continue;
            distance = bDistance;
            pos = blockPos;
        }
        return pos;
    }
}
