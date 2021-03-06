package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

/**
 * 
 * @author Adanaran
 * 
 */
public class ItemMillstone extends Item {

	public ItemMillstone(int id) {
		super(id);
		this.setMaxDamage(131);
		this.setMaxStackSize(16);
	}

	@Override
	public void registerIcons(IconRegister iRegister) {
		itemIcon = iRegister.registerIcon("bfr:itemMillstone");
	}
}
