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
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCatDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Trait;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TRAIT, PREFIX_LOCATION, PREFIX_HEALTH);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
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
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editCatDescriptor);
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
