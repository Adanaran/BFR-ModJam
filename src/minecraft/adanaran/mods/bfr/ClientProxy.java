package adanaran.mods.bfr;

import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.gui.GUIStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

/**
 * 
 * @author Adanaran
 */
public class ClientProxy extends Proxy {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		Object gui;
		switch (ID) {
		case 1:
			gui = new GUIStove(player.inventory, (TileEntityStove) world.getBlockTileEntity(x, y, z), world);
			break;
		default:
			gui = null;
			break;
		}
		return gui;
	}

}
