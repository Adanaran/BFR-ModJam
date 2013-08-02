package adanaran.mods.bfr;

import adanaran.mods.bfr.container.ContainerStove;
import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.gui.GUIStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * 
 * @author Adanaran
 */
public class Proxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		System.out.println("serverProxy called with id: " + ID);
		return new ContainerStove(player.inventory,
				(TileEntityStove) world.getBlockTileEntity(x, y, z));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

}
