package adanaran.mods.bfr.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * 
 * @author Adanaran
 * @author Demitreus
 */
public abstract class ItemCookwareBase extends Item {

	protected EnumToolMaterial enumMat;
	protected Icon itemIcon;
	protected Icon itemIconStone;
	protected Icon itemIconGold;
	protected Icon itemIconDiamond;
	
	public ItemCookwareBase(int id, EnumToolMaterial material) {
		super(id);
		enumMat = material;
		this.setMaxDamage(30);
	}
	@Override
	public Icon getIconFromDamage(int damage) {
		Icon icon = itemIcon;
		switch (enumMat) {
		case STONE:icon = itemIconStone;
			break;
		case IRON:icon = itemIcon;
			break;
		case GOLD:icon = itemIconGold;
			break;
		case EMERALD:icon = itemIconDiamond;
			break;
		case WOOD:
			break;
		default:
			break;
		}
		return icon;
	}
	
}
