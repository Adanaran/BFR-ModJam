package adanaran.mods.bfr.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

/**
 * @author Adanaran
 * @author Demitreus
 *
 */
public class ItemCakePan extends ItemCookwareBase {

	/**
	 * @param id
	 */
	public ItemCakePan(int id, EnumToolMaterial material) {
		super(id, material);
	}
	
	@Override
	public void registerIcons(IconRegister iRegister) {
		itemIconIron = iRegister.registerIcon("bfr:itemCakePan");
		itemIconStone = iRegister.registerIcon("bfr:stone_itemCakePan");
		itemIconGold = iRegister.registerIcon("bfr:gold_itemCakePan");
		itemIconDiamond = iRegister.registerIcon("bfr:diamond_itemCakePan");
	}
}
