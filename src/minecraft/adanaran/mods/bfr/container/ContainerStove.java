package adanaran.mods.bfr.container;

import adanaran.mods.bfr.entities.TileEntityStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerStove extends Container {
	
	 /** The cooking matrix inventory (3x3). */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	private InventoryPlayer invPlayer;
	private TileEntity stove;

	public ContainerStove(InventoryPlayer inventory,
			TileEntityStove tileEntityStove) {
		invPlayer = inventory;
		stove = tileEntityStove;   
        this.addSlotToContainer(new Slot(tileEntityStove, 0, 8, 17));
        this.addSlotToContainer(new Slot(tileEntityStove, 1, 8, 53));
        this.addSlotToContainer(new SlotFurnace(invPlayer.player, tileEntityStove, 2, 124, 35));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(this.invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
            
            for (int i1 = 0; i1 < 3; ++i1)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + i * 3, 34 + i1 * 18, 17 + i * 18));
            }
        }
        
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		
		return true;
	}

}
