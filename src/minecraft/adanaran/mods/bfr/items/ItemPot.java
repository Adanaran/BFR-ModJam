package adanaran.mods.bfr.items;

import adanaran.mods.bfr.BFR;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * 
 * @author Adanaran
 * @author Demitreus
 */
public class ItemPot extends ItemCookwareBase {
	
	public ItemPot(int id, EnumToolMaterial material) {
		super(id, material);
	}

	@Override
	public void registerIcons(IconRegister iRegister) {
		itemIcon = iRegister.registerIcon("bfr:itemPot");
		itemIconStone = iRegister.registerIcon("bfr:stone_itemPot");
		itemIconGold = iRegister.registerIcon("bfr:gold_itemPot");
		itemIconDiamond = iRegister.registerIcon("bfr:diamond_itemPot");
	}
}
