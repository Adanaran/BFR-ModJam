package adanaran.mods.bfr.gui;

import adanaran.mods.bfr.entities.TileEntityMill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class ContainerMill extends Container {

	public ContainerMill(InventoryPlayer invPlayer,
			TileEntityMill tileEntityMill, World world) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

}
