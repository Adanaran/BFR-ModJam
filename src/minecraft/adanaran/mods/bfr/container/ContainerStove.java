package adanaran.mods.bfr.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerStove extends Container {
	
	private InventoryPlayer invPlayer;
	private TileEntity tileEntity;

	public ContainerStove(InventoryPlayer inventory,
			TileEntityFurnace blockTileEntity) {
		invPlayer = inventory;
		tileEntity = blockTileEntity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		
		return true;
	}

}
