package adanaran.mods.bfr;

import adanaran.mods.bfr.gui.GUIStove;
import net.minecraft.entity.player.EntityPlayer;
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
		// TODO hier was vern�nftiges zur�ckgeben. null resultiert in crash.
		return new GUIStove();
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

}
