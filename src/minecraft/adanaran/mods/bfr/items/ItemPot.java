package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;

/**
 * 
 * @author Adanaran
 */
public class ItemPot extends ItemCookwareBase {

	public ItemPot(int id) {
		super(id);
	}
	@Override
	public void registerIcons(IconRegister iRegister) {
		this.itemIcon = iRegister.registerIcon("bfr:itemPot");
	}
}
