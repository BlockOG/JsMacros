package xyz.wagyourtail.jsmacros.client.api.helpers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Etheradon
 * @since 1.6.5
 */
public class BlockHelper extends BaseHelper<Block> {

    public BlockHelper(Block base) {
        super(base);
    }

    /**
     * @return the default state of the block.
     *
     * @version 1.6.5
     */
    public BlockStateHelper getDefaultState() {
        return new BlockStateHelper(base.getDefaultState());
    }

    /**
     * @return the default item stack of the block.
     *
     * @version 1.6.5
     */
    public ItemStackHelper getDefaultItemStack() {
        return new ItemStackHelper(new ItemStack(base.getPickItem(null, null)));
    }

    public boolean canMobSpawnInside() {
        return base.canMobSpawnInside();
    }

    /**
     * @return {@code true} if the block has dynamic bounds.
     *
     * @version 1.6.5
     */
    public boolean hasDynamicBounds() {
        return false;
    }

    /**
     * @return the blast resistance.
     *
     * @version 1.6.5
     */
    public float getBlastResistance() {
        return base.getBlastResistance(null);
    }

    /**
     * @return the jump velocity multiplier.
     *
     * @version 1.6.5
     */
    public float getJumpVelocityMultiplier() {
        return 1;
    }

    /**
     * @return the slipperiness.
     *
     * @version 1.6.5
     */
    public float getSlipperiness() {
        return base.slipperiness;
    }

    /**
     * @return the hardness.
     *
     * @version 1.6.5
     */
    public float getHardness() {
        return base.getDefaultState().getBlock().getHarvestLevel(base.getDefaultState());
    }

    /**
     * @return the velocity multiplier.
     *
     * @version 1.6.5
     */
    public float getVelocityMultiplier() {
        return 1;
    }

    /**
     * @return all tags of the block as an {@link java.util.ArrayList ArrayList}.
     *
     * @version 1.6.5
     */
    public List<String> getTags() {
        return new ArrayList<>();
    }

    /**
     * @return all possible block states of the block.
     *
     * @version 1.6.5
     */
    public List<BlockStateHelper> getStates() {
        return base.getStateManager().getBlockStates().stream().map(BlockStateHelper::new).collect(Collectors.toList());
    }

    /**
     * @return the identifier of the block.
     *
     * @version 1.6.5
     */
    public String getId() {
        return Block.REGISTRY.getIdentifier(base).toString();
    }

    @Override
    public String toString() {
        return String.format("BlockDataHelper:{%s}", getId());
    }

}
