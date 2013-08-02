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
		System.out.println("Proxy called with id: " + ID);
		Object gui;
		switch (ID) {
		case 1:
			System.out.println("Gui called");
			gui = new GUIStove(player.inventory, (TileEntityStove) world.getBlockTileEntity(x, y, z));
			break;
		default:
			gui = null;
			break;
		}
		return gui;
	}

}
