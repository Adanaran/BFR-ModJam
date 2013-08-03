package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

/**
 * 
 * @author Adanaran
 * @author Demitreus
 */
public class ItemPan extends ItemCookwareBase{

	public ItemPan(int id, EnumToolMaterial material) {
		super(id, material);
	}

	@Override
	public void registerIcons(IconRegister iRegister) {
		itemIcon = iRegister.registerIcon("bfr:itemPan");
		itemIconStone = iRegister.registerIcon("bfr:stone_itemPan");
		itemIconGold = iRegister.registerIcon("bfr:gold_itemPan");
		itemIconDiamond = iRegister.registerIcon("bfr:diamond_itemPan");
	}
}
