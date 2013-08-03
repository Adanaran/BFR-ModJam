package adanaran.mods.bfr.entities;

import adanaran.mods.bfr.blocks.BlockStove;
import adanaran.mods.bfr.inventory.ContainerStove;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Adanaran
 */
public class TileEntityStove extends TileEntity implements ISidedInventory {

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };

	/**
	 * The ItemStacks that hold the items currently being used in the stove 0 -
	 * cookware 1 - fuel 2 - result 3-11 - cookingfield
	 */
	private ItemStack[] stoveItemStacks = new ItemStack[12];
	public ContainerStove container;
	private String tEntityName;

	/** The number of ticks that the stove will keep burning */
	public int stoveBurnTime;

	/**
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the stove burning for
	 */
	public int currentItemBurnTime;

	/** The number of ticks that the current item has been cooking for */
	public int stoveCookTime;

	private boolean isCookFieldEmpty() {
		boolean empty = true;
		int i = 3;
		while (i <= 11 && empty == true) {
			empty = stoveItemStacks[i++] == null;
		}
		return empty;
	}

	public boolean canCook() {
		if (stoveItemStacks[0] != null && !isCookFieldEmpty()) {
			if (container == null)
				return false;
			ItemStack result = container.getCookResult(stoveItemStacks);
			if (result == null)
				return false;
			if (this.stoveItemStacks[2] == null)
				return true;
			if (!this.stoveItemStacks[2].isItemEqual(result))
				return false;
			int res = stoveItemStacks[2].stackSize + result.stackSize;
			return (res <= getInventoryStackLimit() && res <= result
					.getMaxStackSize());
		} else {
			return false;
		}
	}

	public void cookItem() {
		if (canCook()) {
			// damage Cookware
			stoveItemStacks[1].damageItem(1, container.invPlayer.player);
			// add cookResult
			ItemStack result = container.getCookResult(stoveItemStacks);
			if (stoveItemStacks[2] == null) {
				stoveItemStacks[2] = result.copy();
			} else if (this.stoveItemStacks[2].isItemEqual(result)) {
				stoveItemStacks[2].stackSize += result.stackSize;
			}
			// damage cookware
			this.stoveItemStacks[0].damageItem(1, container.invPlayer.player);

			if (this.stoveItemStacks[0].stackSize <= 0) {
				this.stoveItemStacks[0] = null;
			}
			// decrease items in cooking-Matrix
			for (int i = 3; i <= 11; i++) {
				if (stoveItemStacks[i] != null) {
					if (stoveItemStacks[i].getItem().itemID == Item.bucketMilk.itemID
							|| stoveItemStacks[i].getItem().itemID == Item.bucketWater.itemID) {
						// don't waste buckets
						stoveItemStacks[i] = new ItemStack(Item.bucketEmpty);
					} else {
						stoveItemStacks[i].stackSize--;
					}
					if (stoveItemStacks[i].stackSize <= 0) {
						stoveItemStacks[i] = null;
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1) {
		return this.stoveCookTime * par1 / 200;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 200;
		}

		return this.stoveBurnTime * par1 / this.currentItemBurnTime;
	}

	/**
	 * Returns true if the furnace is currently burning
	 */
	public boolean isBurning() {
		return this.stoveBurnTime > 0;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.stoveItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.stoveItemStacks.length) {
				this.stoveItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.stoveBurnTime = par1NBTTagCompound.getShort("BurnTime");
		this.stoveCookTime = par1NBTTagCompound.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.stoveItemStacks[1]);

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.tEntityName = par1NBTTagCompound.getString("CustomName");
		}
	}

	/**
	 * Returns the name of the inventory.
	 */
	public String getInvName() {
		return this.isInvNameLocalized() ? this.tEntityName : "container.stove";
	}

	/**
	 * If this returns false, the inventory name will be used as an unlocalized
	 * name, and translated into the player's language. Otherwise it will be
	 * used directly.
	 */
	public boolean isInvNameLocalized() {
		return this.tEntityName != null && this.tEntityName.length() > 0;
	}

	/**
	 * Sets the custom display name to use when opening a GUI linked to this
	 * tile entity.
	 */
	public void setGuiDisplayName(String par1Str) {
		this.tEntityName = par1Str;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) this.stoveBurnTime);
		par1NBTTagCompound.setShort("CookTime", (short) this.stoveCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.stoveItemStacks.length; ++i) {
			if (this.stoveItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.stoveItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.isInvNameLocalized()) {
			par1NBTTagCompound.setString("CustomName", this.tEntityName);
		}
	}

	@Override
	public int getSizeInventory() {
		return this.stoveItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.stoveItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.stoveItemStacks[i] != null) {
			ItemStack itemstack;

			if (this.stoveItemStacks[i].stackSize <= j) {
				itemstack = this.stoveItemStacks[i];
				this.stoveItemStacks[i] = null;
				return itemstack;
			} else {
				itemstack = this.stoveItemStacks[i].splitStack(j);

				if (this.stoveItemStacks[i].stackSize == 0) {
					this.stoveItemStacks[i] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.stoveItemStacks[i] != null) {
			ItemStack itemstack = this.stoveItemStacks[i];
			this.stoveItemStacks[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.stoveItemStacks[i] = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : entityplayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		// Without function
	}

	@Override
	public void closeChest() {
		// Without function
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */

	public void updateEntity() {
		boolean burning = this.stoveBurnTime > 0;
		boolean flag1 = false;

		if (this.stoveBurnTime > 0) {
			--this.stoveBurnTime;
		}

		if (!this.worldObj.isRemote) {
			if (this.stoveBurnTime == 0 && this.canCook()) {
				this.currentItemBurnTime = this.stoveBurnTime = getItemBurnTime(this.stoveItemStacks[1]);

				if (this.stoveBurnTime > 0) {
					flag1 = true;

					if (this.stoveItemStacks[1] != null) {
						--this.stoveItemStacks[1].stackSize;

						if (this.stoveItemStacks[1].stackSize == 0) {
							// if fuel empty
							this.stoveItemStacks[1] = this.stoveItemStacks[1]
									.getItem().getContainerItemStack(
											stoveItemStacks[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canCook()) {
				++this.stoveCookTime;

				if (this.stoveCookTime == 200) {
					// if cooked long enough
					this.stoveCookTime = 0;
					this.cookItem();
					flag1 = true;
				}
			} else {
				this.stoveCookTime = 0;
			}

			if (burning != this.stoveBurnTime > 0) {
				flag1 = true;
				BlockStove.updateStoveBlockState(this.stoveBurnTime > 0,
						this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1) {
			this.onInventoryChanged();
		}
	}

	/**
	 * Return true if item is a fuel source (getItemBurnTime() > 0).
	 */
	public static boolean isItemFuel(ItemStack par0ItemStack) {
		return getItemBurnTime(par0ItemStack) > 0;
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the
	 * furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack par0ItemStack) {
		if (par0ItemStack == null) {
			return 0;
		} else {
			int i = par0ItemStack.getItem().itemID;
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock
					&& Block.blocksList[i] != null) {
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab) {
					return 150;
				}

				if (block.blockMaterial == Material.wood) {
					return 300;
				}

				if (block == Block.field_111034_cE) {
					return 16000;
				}
			}

			if (item instanceof ItemTool
					&& ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword
					&& ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe
					&& ((ItemHoe) item).getMaterialName().equals("WOOD"))
				return 200;
			if (i == Item.stick.itemID)
				return 100;
			if (i == Item.coal.itemID)
				return 1600;
			if (i == Item.bucketLava.itemID)
				return 20000;
			if (i == Block.sapling.blockID)
				return 100;
			if (i == Item.blazeRod.itemID)
				return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int j) {
		return j != 0 || slot != 1
				|| itemstack.itemID == Item.bucketEmpty.itemID;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 2 ? false : (i == 1 ? isItemFuel(itemstack) : true);
	}

}
