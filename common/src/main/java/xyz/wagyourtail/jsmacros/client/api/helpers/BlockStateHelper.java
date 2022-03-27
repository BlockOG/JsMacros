package xyz.wagyourtail.jsmacros.client.api.helpers;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Util;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Etheradon
 * @since 1.6.5
 */
public class BlockStateHelper extends BaseHelper<BlockState> {

    public BlockStateHelper(BlockState base) {
        super(base);
    }

    /**
     * @return a map of the state properties with its identifier and value.
     *
     * @version 1.6.5
     */
    public Map<String, String> toMap() {
        return base.getProperties().stream().collect(Collectors.toMap(IProperty::getName, entry -> Objects.toString(base.get(entry), "null")));
    }

    /**
     * @return the block the state belongs to.
     *
     * @version 1.6.5
     */
    public BlockHelper getBlock() {
        return new BlockHelper(base.getBlock());
    }

    /**
     * @return the hardness.
     *
     * @version 1.6.5
     */
    public float getHardness() {
        return base.getHardness(null, null);
    }

    /**
     * @return the luminance.
     *
     * @version 1.6.5
     */
    public int getLuminance() {
        return base.getLuminance();
    }

    /**
     * @return {@code true} if the state emits redstone power.
     *
     * @version 1.6.5
     */
    public boolean emitsRedstonePower() {
        return base.emitsRedstonePower();
    }

    /**
     * @return {@code true} if the shape of the state is a cube.
     *
     * @version 1.6.5
     */
    public boolean exceedsCube() {
        return base.method_17900();
    }

    /**
     * @return {@code true} if the state is air.
     *
     * @version 1.6.5
     */
    public boolean isAir() {
        return base.isAir();
    }

    /**
     * @return {@code true} if the state is opaque.
     *
     * @version 1.6.5
     */
    public boolean isOpaque() {
        return base.isOpaque();
    }

    /**
     * @return {@code true} if a tool is required to mine the block.
     *
     * @version 1.6.5
     */
    public boolean isToolRequired() {
        return !base.getMaterial().canBreakByHand();
    }

    /**
     * @return {@code true} if the state has a block entity.
     *
     * @version 1.6.5
     */
    public boolean hasBlockEntity() {
        return base instanceof BlockEntityProvider;
    }

    /**
     * @return {@code true} if the state can be random ticked.
     *
     * @version 1.6.5
     */
    public boolean hasRandomTicks() {
        return base.hasRandomTicks();
    }

    /**
     * @return {@code true} if the state has a comparator output.
     *
     * @version 1.6.5
     */
    public boolean hasComparatorOutput() {
        return base.hasComparatorOutput();
    }

    /**
     * @return the piston behaviour of the state.
     *
     * @version 1.6.5
     */
    public String getPistonBehaviour() {
        System.out.println(this + " " + base.getPistonBehavior());
        switch (base.getPistonBehavior()) {
            case NORMAL:
                return "NORMAL";
            case BLOCK:
                return "BLOCK";
            case PUSH_ONLY:
                return "PUSH_ONLY";
            case DESTROY:
                return "DESTROY";
            case IGNORE:
                return "IGNORE";
            default:
                throw new IllegalStateException("Unexpected value: " + base.getPistonBehavior());
        }
    }

    /**
     * @return {@code true} if the state blocks light.
     *
     * @version 1.6.5
     */
    public boolean blocksLight() {
        return base.getMaterial().blocksLight();
    }

    /**
     * @return {@code true} if the state blocks the movement of entities.
     *
     * @version 1.6.5
     */
    public boolean blocksMovement() {
        return base.getMaterial().blocksMovement();
    }

    /**
     * @return {@code true} if the state is burnable.
     *
     * @version 1.6.5
     */
    public boolean isBurnable() {
        return base.getMaterial().isBurnable();
    }

    /**
     * @return {@code true} if the state is a liquid.
     *
     * @version 1.6.5* @version 1.6.5
     */
    public boolean isLiquid() {
        return base.getMaterial().isLiquid();
    }

    /**
     * @return {@code true} if the state is solid.
     *
     * @version 1.6.5* @version 1.6.5
     */
    public boolean isSolid() {
        return base.getMaterial().isSolid();
    }

    /**
     * This will return true for blocks like air and grass, that can be replaced
     * without breaking them first.
     *
     * @return {@code true} if the state can be replaced.
     *
     * @version 1.6.5
     */
    public boolean isReplaceable() {
        return base.getMaterial().isReplaceable();
    }

    /**
     * @param pos
     * @param entity
     * @return {@code true} if the entity can spawn on this block state at the given position in the current world.
     *
     * @version 1.6.5
     */
    public boolean allowsSpawning(BlockPosHelper pos, String entity) {
        return base.allowsSpawning(MinecraftClient.getInstance().world, pos.getRaw(), Registry.ENTITY_TYPE.get(new Identifier(entity)));
    }

    /**
     * @param pos
     * @return {@code true} if an entity can suffocate in this block state at the given position in the current world.
     *
     * @version 1.6.5
     */
    public boolean shouldSuffocate(BlockPosHelper pos) {
        return base.canSuffocate(MinecraftClient.getInstance().world, pos.getRaw());
    }

    @Override
    public String toString() {
        return String.format("BlockStateHelper:{%s, %s}", getBlock().getId(), toMap());
    }

}
