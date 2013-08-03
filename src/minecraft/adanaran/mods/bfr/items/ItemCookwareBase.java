package adanaran.mods.bfr.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

/**
 * 
 * @author Adanaran
 */
public abstract class ItemCookwareBase extends Item {

	protected EnumToolMaterial enumMat;

	public ItemCookwareBase(int id) {
		super(id);
		this.setMaxDamage(30);
	}

}
