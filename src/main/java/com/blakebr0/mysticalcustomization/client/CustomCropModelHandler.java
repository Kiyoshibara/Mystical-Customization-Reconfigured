package com.blakebr0.mysticalcustomization.client;

import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.CropType;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.blakebr0.mysticalcustomization.MysticalCustomization;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registers and assigns block models for user-created crop types.
 *
 * The Mystical Agriculture 26.1/26.3 model handler only registers stem model
 * keys for the built-in RESOURCE and MOB crop types. A custom type therefore
 * receives the missing-model cube even when its stem model path is valid.
 * This handler fixes custom types without modifying the Agriculture JAR.
 *
 * A custom stem model path must resolve to 8 existing block models named
 * <stem>_0 ... <stem>_7, supplied either by Mystical Agriculture or a pack.
 */
public final class CustomCropModelHandler {
    private final Map<CropType, List<StandaloneModelKey<BlockStateModel>>> stemModels = new IdentityHashMap<>();
    private final Map<Identifier, StandaloneModelKey<BlockStateModel>> registeredModels = new HashMap<>();

    @SubscribeEvent
    public void onRegisterStandalone(ModelEvent.RegisterStandalone event) {
        this.stemModels.clear();
        this.registeredModels.clear();

        for (var type : CropRegistry.getInstance().getTypes()) {
            if (isBuiltIn(type))
                continue;

            var models = new ArrayList<StandaloneModelKey<BlockStateModel>>(7);
            for (int age = 0; age < 7; age++) {
                var stemModel = type.getStemModel().withSuffix("_" + age);
                models.add(this.register(event, stemModel));
            }
            this.stemModels.put(type, models);
        }

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (!isBuiltIn(crop.getType())) {
                this.register(event, getFlowerModel(crop));
            }
        }
    }

    @SubscribeEvent
    public void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        var baked = event.getBakingResult();
        int fixed = 0;

        for (var crop : CropRegistry.getInstance().getCrops()) {
            var keys = this.stemModels.get(crop.getType());
            if (keys == null)
                continue;

            for (int age = 0; age < 7; age++) {
                var model = baked.standaloneModels().get(keys.get(age));
                if (model != null && model != baked.missingModels().block()) {
                    baked.blockStateModels().put(crop.getCropBlock().getStateForAge(age), model);
                    fixed++;
                }
            }

            var flowerKey = this.registeredModels.get(getFlowerModel(crop));
            if (flowerKey != null) {
                var model = baked.standaloneModels().get(flowerKey);
                if (model != null && model != baked.missingModels().block()) {
                    baked.blockStateModels().put(crop.getCropBlock().getStateForAge(7), model);
                    fixed++;
                }
            }
        }

        if (fixed > 0) {
            MysticalCustomization.LOGGER.info("Restored {} custom crop block-state models", fixed);
        }
    }

    private StandaloneModelKey<BlockStateModel> register(ModelEvent.RegisterStandalone event, Identifier id) {
        var existing = this.registeredModels.get(id);
        if (existing != null)
            return existing;

        var key = new StandaloneModelKey<BlockStateModel>(new Key(id));
        event.register(key, SimpleUnbakedStandaloneModel.blockStateModel(id));
        this.registeredModels.put(id, key);
        return key;
    }

    private static Identifier getFlowerModel(Crop crop) {
        var flower = crop.getModels().getFlowerModel();
        var stem = crop.getType().getStemModel();

        // MA provides model variants for resource and mob stem silhouettes.
        // Use the matching existing model instead of looking for a nonexistent
        // <flower>_<new type name> model for JSON-created crop types.
        if (flower.getNamespace().equals("mysticalagriculture")
                && isBuiltInFlower(flower.getPath())) {
            if (stem.equals(CropType.MOB.getStemModel()))
                return flower.withSuffix("_mob");
            if (stem.equals(CropType.RESOURCE.getStemModel()))
                return flower.withSuffix("_resource");
        }

        // Resource packs can provide a per-type flower variant at this path.
        return flower.withSuffix("_" + crop.getType().getName());
    }

    private static boolean isBuiltInFlower(String path) {
        return path.equals("block/flower_ingot")
                || path.equals("block/flower_rock")
                || path.equals("block/flower_dust")
                || path.equals("block/flower_face");
    }

    private static boolean isBuiltIn(CropType type) {
        return type == CropType.MOB || type == CropType.RESOURCE;
    }

    private record Key(Identifier id) implements ModelDebugName {
        @Override
        public String debugName() {
            return "mysticalcustomization:custom/" + this.id;
        }
    }
}
