package adanaran.mods.bfr.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;

public class SlotStove extends SlotFurnace {

	SlotStove(EntityPlayer thePlayer, IInventory inventory, int slotIndex,
			int posX, int posY) {
		super(thePlayer, inventory, slotIndex, posX, posY);
	}

}
