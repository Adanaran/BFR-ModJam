package adanaran.mods.bfr.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import adanaran.mods.bfr.crafting.BFRCraftingManager;
import adanaran.mods.bfr.entities.TileEntityStove;
import adanaran.mods.bfr.items.ItemCookwareBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Demitreus
 *
 */
public class ContainerStove extends Container {

	public InventoryPlayer invPlayer;
	private TileEntityStove stove;
	private World worldObj;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	private ItemStack cookResult;

	public ContainerStove(InventoryPlayer inventory,
			TileEntityStove tileEntityStove, World world) {
		invPlayer = inventory;
		stove = tileEntityStove;
		this.worldObj = world;
		this.addSlotToContainer(new Slot(tileEntityStove, 1, 8, 53));
		this.addSlotToContainer(new SlotCookware(tileEntityStove, 0, 8, 17));
		this.addSlotToContainer(new SlotFurnace(inventory.player,
				tileEntityStove, 2, 124, 35));
		int i;
		int slotIndex = 3;
		for (i = 0; i < 3; ++i) {
			for (int i1 = 0; i1 < 3; ++i1) {
				// 3x3 Cooking Field; slot 3 - 11
				this.addSlotToContainer(new Slot(tileEntityStove, slotIndex++,
						34 + i1 * 18, 17 + i * 18));
			}
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				// Playerinventory; slot 12 - 38
				this.addSlotToContainer(new Slot(this.invPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			// Last row of Playerinventory; slot 39 - 47
			this.addSlotToContainer(new Slot(this.invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.stove.stoveCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.stove.stoveBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2,
				this.stove.currentItemBurnTime);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.stove.stoveCookTime) {
				icrafting.sendProgressBarUpdate(this, 0,
						this.stove.stoveCookTime);
			}

			if (this.lastBurnTime != this.stove.stoveBurnTime) {
				icrafting.sendProgressBarUpdate(this, 1,
						this.stove.stoveBurnTime);
			}

			if (this.lastItemBurnTime != this.stove.currentItemBurnTime) {
				icrafting.sendProgressBarUpdate(this, 2,
						this.stove.currentItemBurnTime);
			}
		}
		stove.container = this;
		this.lastCookTime = this.stove.stoveCookTime;
		this.lastBurnTime = this.stove.stoveBurnTime;
		this.lastItemBurnTime = this.stove.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.stove.stoveCookTime = par2;
		}
		if (par1 == 1) {
			this.stove.stoveBurnTime = par2;
		}
		if (par1 == 2) {
			this.stove.currentItemBurnTime = par2;
		}
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that. Returns the clicked Item stack (i
	 * guess).
	 */
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlot) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(clickedSlot);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (clickedSlot <= 11) {
				if (!this.mergeItemStack(itemstack1, 12, 48, false)) {
					return null;
				}
			} else if (clickedSlot >= 39) {
				if (!this.mergeItemStack(itemstack1, 12, 39, false)) {
					return null;
				}
			} else if (clickedSlot >= 12) {
				if (!(itemstack1.getItem() instanceof ItemCookwareBase)) {
					if (TileEntityFurnace.isItemFuel(itemstack1)) {
						if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
							return null;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 3, 12, false)) {
							return null;
						}
					}
				} else if (!((Slot) inventorySlots.get(1)).getHasStack()) {
					itemstack1.stackSize--;
					ItemStack stack = new ItemStack(itemstack1.getItem());
					stack.setItemDamage(itemstack1.getItemDamage());
					if (!this.mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				}
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}

	public ItemStack getCookResult(ItemStack[] itemStacks) {
		InventoryCrafting inventorycrafting = new InventoryCrafting(this, 3, 3);
		for (int i = 3; i <= 11; i++) {
			inventorycrafting.setInventorySlotContents(i - 3, itemStacks[i]);
		}
		cookResult = BFRCraftingManager.getInstance().findMatchingRecipe(
				inventorycrafting, worldObj, itemStacks[0]);
		return cookResult == null ? null : cookResult.copy();
	}
}
