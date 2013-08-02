package adanaran.mods.bfr.inventory;

import adanaran.mods.bfr.items.ItemBetterFoodBase;
import adanaran.mods.bfr.items.ItemCookwareBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class SlotStove extends SlotFurnace {
	
	SlotStove(EntityPlayer thePlayer,IInventory inventory, int slotIndex, int posX, int posY){
		super(thePlayer, inventory, slotIndex, posX, posY);
	}

}
