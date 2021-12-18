package space.rogi27.renameit.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.TextParser;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

// TODO: Move lore manipulations to another function
public class LoreCommand {

    public static int addLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.empty").formatted(Formatting.YELLOW), false);
                return 0;
            }
            Text loreString = PlaceholderAPI.parseText(TextParser.parse(context.getArgument("text", String.class)).shallowCopy().setStyle(Style.EMPTY.withItalic(false)), context.getSource().getPlayer());
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getOrCreateSubNbt("display");

            NbtList lore = new NbtList();
            if (itemNbt.contains("Lore")) {
                lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            }

            lore.add(NbtString.of(Text.Serializer.toJson(loreString)));
            itemNbt.put("Lore", lore);

            context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_added", loreString.shallowCopy().formatted(Formatting.WHITE)).formatted(Formatting.GREEN), false);
            return 1;
        } else {
            return 0;
        }
    }

    public static int setLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendFeedback(new TranslatableText("").formatted(Formatting.YELLOW), false);
                return 0;
            }
            Text loreString = PlaceholderAPI.parseText(TextParser.parse(context.getArgument("text", String.class)).shallowCopy().setStyle(Style.EMPTY.withItalic(false)), context.getSource().getPlayer());
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getSubNbt("display");

            if (!itemNbt.contains("Lore")) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_nolore").formatted(Formatting.YELLOW), false);
                return 0;
            }

            NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            if (lore.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_noline").formatted(Formatting.RED), false);
                return 0;
            }

            lore.set(context.getArgument("line", Integer.class) - 1, NbtString.of(Text.Serializer.toJson(loreString)));
            itemNbt.put("Lore", lore);

            context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_line_changed", context.getArgument("line", Integer.class), loreString.shallowCopy().formatted(Formatting.WHITE)).formatted(Formatting.GREEN), false);
            return 1;
        } else {
            return 0;
        }
    }

    public static int deleteLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.empty").formatted(Formatting.YELLOW), false);
                return 0;
            }
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getSubNbt("display");

            if (!itemNbt.contains("Lore")) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_nolore").formatted(Formatting.YELLOW), false);
                return 0;
            }

            NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
            if (lore.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_noline").formatted(Formatting.RED), false);
                return 0;
            }

            lore.remove(context.getArgument("line", Integer.class) - 1);
            itemNbt.put("Lore", lore);

            context.getSource().sendFeedback(new TranslatableText("text.renameit.lore_deleted", context.getArgument("line", Integer.class)).formatted(Formatting.GREEN), false);
            return 1;
        } else {
            return 0;
        }
    }
}
