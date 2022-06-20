package net.dakotapride.incantation.common.block.entity;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.inventory.ImplementedInventory;
import net.dakotapride.incantation.common.recipe.BewitchmentTableRecipe;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BewitchmentTableEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(8, ItemStack.EMPTY);


    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 288;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public BewitchmentTableEntity(BlockPos pos, BlockState state) {
        super(IncantationMod.BEWITCHMENT_TABLE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return BewitchmentTableEntity.this.progress;
                    case 1: return BewitchmentTableEntity.this.maxProgress;
                    case 2: return BewitchmentTableEntity.this.fuelTime;
                    case 3: return BewitchmentTableEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: BewitchmentTableEntity.this.progress = value; break;
                    case 1: BewitchmentTableEntity.this.maxProgress = value; break;
                    case 2: BewitchmentTableEntity.this.fuelTime = value; break;
                    case 3: BewitchmentTableEntity.this.maxFuelTime = value; break;
                }
            }

            public int size() {
                return 8;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.moreweaponry.echo_infuser.label");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BewitchmentTableScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("echo_infuser.progress", progress);
        nbt.putInt("echo_infuser.fuelTime", fuelTime);
        nbt.putInt("echo_infuser.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("echo_infuser.progress");
        fuelTime = nbt.getInt("echo_infuser.fuelTime");
        maxFuelTime = nbt.getInt("echo_infuser.maxFuelTime");
    }

    public static void tick(World world, BlockPos pos, BlockState state, BewitchmentTableEntity entity) {
            if(hasRecipe(entity)) {
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        else {
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(BewitchmentTableEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<BewitchmentTableRecipe> match = world.getRecipeManager()
                .getFirstMatch(BewitchmentTableRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(BewitchmentTableEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<BewitchmentTableRecipe> match = world.getRecipeManager()
                .getFirstMatch(BewitchmentTableRecipe.Type.INSTANCE, inventory, world);

        if(match.isPresent()) {
            entity.removeStack(1,1);
            entity.removeStack(2,1);
            entity.removeStack(3,1);
            entity.removeStack(4,1);
            entity.removeStack(5,1);
            entity.removeStack(6,1);

            entity.setStack(7, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(7).getCount() + 1));


            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(8).getItem() == output.getItem() || inventory.getStack(8).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(8).getMaxCount() > inventory.getStack(8).getCount();
    }

}
