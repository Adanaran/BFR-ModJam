package adanaran.mods.bfr;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.inventory.ContainerStove;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * 
 * @author Adanaran
 */
public class Proxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		Object ret;
		switch (ID) {
		case 1:
			ret = new ContainerStove(player.inventory,
					(TileEntityStove) world.getBlockTileEntity(x, y, z), world);
			break;
		default:
			ret = null;
		}
		return ret;

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

}
