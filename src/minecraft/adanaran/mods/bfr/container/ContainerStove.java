package adanaran.mods.bfr.container;

import adanaran.mods.bfr.entities.TileEntityStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerStove extends Container {
	
	private InventoryPlayer invPlayer;
	private TileEntity tileEntity;

	public ContainerStove(InventoryPlayer inventory,
			TileEntityStove tileEntityStove) {
		invPlayer = inventory;
		tileEntity = tileEntityStove;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		
		return true;
	}

}
