package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemMillstone extends Item {

	public ItemMillstone(int id) {
		super(id);
		this.setMaxDamage(131);
	}

	@Override
	public void registerIcons(IconRegister iRegister) {
		iRegister.registerIcon("bfr:itemMillstone");
		this.setMaxDamage(100);
	}
}
