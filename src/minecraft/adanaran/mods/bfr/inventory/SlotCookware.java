package adanaran.mods.bfr.inventory;

import adanaran.mods.bfr.items.ItemCookwareBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCookware extends Slot {

	public SlotCookware(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
	return itemStack.getItem() instanceof ItemCookwareBase;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
}
