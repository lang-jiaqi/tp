package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * Parses input arguments and creates a new UpdateCommand object.
 * Supports identifying the target cat by index number or by name.
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * The preamble is treated as an index if it is a positive integer, otherwise as a cat name.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TRAIT, PREFIX_LOCATION, PREFIX_HEALTH);

        String preamble = argMultimap.getPreamble().trim();

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LOCATION, PREFIX_HEALTH);

        EditCatDescriptor editCatDescriptor = new EditCatDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCatDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editCatDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_HEALTH).isPresent()) {
            editCatDescriptor.setHealth(ParserUtil.parseHealth(argMultimap.getValue(PREFIX_HEALTH).get()));
        }
        parseTraitsForEdit(argMultimap.getAllValues(PREFIX_TRAIT)).ifPresent(editCatDescriptor::setTraits);

        if (!editCatDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }

        // Try to parse preamble as a 1-based index; fall back to name if not a number.
        try {
            Index index = ParserUtil.parseIndex(preamble);
            return new UpdateCommand(index, editCatDescriptor);
        } catch (ParseException e) {
            // Not a valid index — treat as cat name
        }

        try {
            Name name = ParserUtil.parseName(preamble);
            return new UpdateCommand(name, editCatDescriptor);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Parses {@code Collection<String> traits} into a {@code List<Trait>} if {@code traits} is non-empty.
     * If {@code traits} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Trait>} containing zero traits.
     */
    private Optional<List<Trait>> parseTraitsForEdit(Collection<String> traits) throws ParseException {
        assert traits != null;

        if (traits.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> traitList = traits.size() == 1 && traits.contains("") ? Collections.emptyList() : traits;
        return Optional.of(ParserUtil.parseTraits(traitList));
    }

}
