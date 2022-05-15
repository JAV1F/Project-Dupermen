package com.ja.dupermen.module.client;

import com.ja.dupermen.event.PacketEvent;
import com.ja.dupermen.module.Category;
import com.ja.dupermen.module.Module;
import com.ja.dupermen.setting.Setting;
import com.ja.dupermen.util.PlayerUtils;
import com.ja.dupermen.util.Timer;
import me.bush.eventbus.annotation.EventListener;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class AutoDuper extends Module {
    Setting mode = new Setting("Mode", new String[] {
            "Minecart Entity Container",
            "Vanilla Shulker"
    });
    Setting interactRange = new Setting("Interact Range", 5, 0, 6, false);
    Setting timeoutTime = new Setting("Timeout", 5, 0, 6, false);
    Setting delay = new Setting("Delay", 1, 0, 6, false);
    Setting dismount = new Setting("Dismount",false);

    public AutoDuper() {
        super("Auto Duper", "Automaticly duplicates items for you", Category.DUPERMEN);
        addSetting(mode);
        addSetting(interactRange);
        addSetting(timeoutTime);
        addSetting(delay);
    }
    boolean cancel;
    Timer timeout = new Timer();

    public void onEnable() {
        cancel = false;
    }

    public void onUpdate() {
        if (fullNullCheck() || mode.getMVal() == 1) return;
        Entity riding = mc.player.ridingEntity;
        EntityDonkey donkey = null;
        EntityMinecart minecart = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player.getDistance(entity) > interactRange.getDVal()) continue;
            if (entity instanceof EntityDonkey && ((EntityDonkey) entity).hasChest() && mc.world.isAreaLoaded(new BlockPos(entity.getPositionEyes(mc.getRenderPartialTicks())), 0) && entity != riding) donkey = (EntityDonkey) entity;
            if (entity instanceof EntityMinecart && entity != riding) minecart = (EntityMinecart) entity;
        }

        BlockPos woodButton = PlayerUtils.getBlock(Blocks.WOODEN_BUTTON, interactRange.getDVal());
        BlockPos stoneButton = PlayerUtils.getBlock(Blocks.STONE_BUTTON, interactRange.getDVal());
        BlockPos chest = PlayerUtils.getBlock(Blocks.CHEST, interactRange.getDVal());

        if (!(riding instanceof EntityMinecart) && minecart != null && !cancel) mc.player.connection.sendPacket(new CPacketUseEntity(minecart, EnumHand.MAIN_HAND));

        if (riding instanceof EntityMinecart && isFrozen(riding) && timeout.hasReached((long) (timeoutTime.getDVal() * 1000))) {
            if (!cancel && stoneButton != null) {
                if (donkey == null) cancel = true; else timeout.reset();
                mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(stoneButton, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
            } else if (cancel && woodButton != null && donkey != null) {
                mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(woodButton, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                mount(donkey);
            }
            timeout.reset();
        }

        if ((riding instanceof AbstractChestHorse || donkey != null) && woodButton != null && timeout.hasReached(40000) && cancel) {
            mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(woodButton, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
        }
        if ((riding instanceof AbstractChestHorse || donkey != null) && timeout.hasReached(60000) && cancel) cancel = false;

        if (riding instanceof AbstractChestHorse && woodButton != null && donkey != null && chest != null && ((EntityDonkey) riding).hasChest() && timeout.hasReached((long) (delay.getDVal() * 1000)) && cancel) {
            ContainerHorseChest horseChest = ((EntityDonkey) riding).horseChest;
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest container = (GuiChest) mc.currentScreen;
                for (int i = 54; i < 90; i++) {
                    Item item = container.inventorySlots.getSlot(i).getStack().item;
                    if (!(item instanceof ItemBlock && ((ItemBlock) item).block instanceof BlockShulkerBox))
                        continue;
                    mc.playerController.windowClick(container.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                    return;
                }
                mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(woodButton, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                mount(donkey);
                mc.currentScreen.onGuiClosed();
                mc.player.connection.sendPacket(new CPacketCloseWindow(container.inventorySlots.windowId));
            } else {
                if (mc.currentScreen instanceof GuiScreenHorseInventory) {
                    GuiScreenHorseInventory container = (GuiScreenHorseInventory) mc.currentScreen;
                    for (int i = 0; i < container.horseInventory.getSizeInventory(); ++i) {
                        Item item = container.horseInventory.getStackInSlot(i).item;
                        if (!(item instanceof ItemBlock && ((ItemBlock) item).block instanceof BlockShulkerBox))
                            continue;
                        mc.playerController.windowClick(container.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                        item = container.horseInventory.getStackInSlot(i).item;
                        if (item instanceof ItemBlock && ((ItemBlock) item).block instanceof BlockShulkerBox) mc.playerController.windowClick(container.inventorySlots.windowId, i, 0, ClickType.THROW, mc.player);
                        return;
                    }
                    mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(chest, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                } else {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.OPEN_INVENTORY));
                    mc.player.openGuiHorseInventory((AbstractHorse) riding, horseChest);
                }
            }
            timeout.reset();
        }
    }

    public void mount(Entity entity) {
        if (dismount.getBVal()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        mc.player.connection.sendPacket(new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
    }

    public boolean isFrozen(Entity entity) {
        return entity.posX == entity.lastTickPosX && entity.posZ == entity.lastTickPosZ;
    }

    @EventListener
    public void onPacketSend(PacketEvent.Receive event) {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketDisconnect) cancel = false;
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (fullNullCheck()) return;
        Packet packet = event.getPacket();
        if (mode.getMVal() == 0)
            if (cancel && (packet instanceof CPacketPlayer || packet instanceof CPacketVehicleMove)) event.setCancelled(true);
            else if (packet instanceof CPacketPlayerDigging && ((CPacketPlayerDigging) packet).action == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK && mc.currentScreen instanceof GuiShulkerBox) {
                GuiShulkerBox shulker = (GuiShulkerBox) mc.currentScreen;
                for (int i = 0; i < shulker.inventory.getSizeInventory(); ++i) {
                    Item item = shulker.inventorySlots.getSlot(i).getStack().item;
                    if (item != Items.AIR) mc.playerController.windowClick(shulker.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                }
            }
    }
}
