package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemFlour extends Item {

	public ItemFlour(int id) {
		super(id);
	}

	@Override
	public void registerIcons(IconRegister iRegister) {
		itemIcon = iRegister.registerIcon("bfr:itemFlour");
	}

}
