package adanaran.mods.bfr.inventory;

import adanaran.mods.bfr.items.ItemBetterFoodBase;
import adanaran.mods.bfr.items.ItemCookwareBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class SlotStove extends SlotFurnace {
	
	private int index;

	SlotStove(EntityPlayer thePlayer,IInventory inventory, int slotIndex, int posX, int posY){
		super(thePlayer, inventory, slotIndex, posX, posY);
		this.index = slotIndex;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
		return index == 0 ? itemStack.getItem() instanceof ItemCookwareBase : false;
	}

	@Override
	public int getSlotStackLimit() {
		return index == 0 ? 1 : 64;
	}
	
	//TODO @Demitreus wieder 2 slots draus machen, einer erbt nich von furnace
}
