package logisticspipes.items;

import java.util.List;

import logisticspipes.LogisticsPipes;
import logisticspipes.blocks.LogisticsSolidBlock;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class LogisticsSolidBlockItem extends ItemBlock {

	public LogisticsSolidBlockItem(int par1) {
		super(par1);
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public String getItemDisplayName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case LogisticsSolidBlock.SOLDERING_STATION:
			return "Soldering Station";
		case LogisticsSolidBlock.LOGISTICS_POWER_JUNCTION:
			return "Logistics Power Junction";
		}
		return super.getItemDisplayName(stack);
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabDecorations;
	}

	@Override
	public int getMetadata(int par1) {
        return par1;
    }
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this,1,0));
		par3List.add(new ItemStack(this,1,1));
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{ getCreativeTab() , LogisticsPipes.LPCreativeTab };
	}
}
