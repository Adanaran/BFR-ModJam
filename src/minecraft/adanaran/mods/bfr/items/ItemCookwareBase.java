package adanaran.mods.bfr.items;

import adanaran.mods.bfr.data.EnumMat;
import net.minecraft.item.Item;

/**
 * 
 * @author Adanaran
 */
public abstract class ItemCookwareBase extends Item {
	
	protected EnumMat enumMat;

	public ItemCookwareBase(int id) {
		super(id);
	}

}
