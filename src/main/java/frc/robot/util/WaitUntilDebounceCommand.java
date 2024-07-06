package frc.robot.util;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.BooleanSupplier;

/**
 * A command that does nothing but ends after a specified match time or
 * condition. Useful for
 * CommandGroups.
 *
 * <p>
 * This class is provided by the NewCommands VendorDep
 */
public class WaitUntilDebounceCommand extends Command {
    private final BooleanSupplier m_condition;
    private Debouncer debouncer;
    private double debounceTime;
    private DebounceType type;

    /**
     * Creates a new WaitUntilCommand that ends after a given condition becomes
     * true.
     *
     * @param condition
     *            the condition to determine when to end
     */
    public WaitUntilDebounceCommand(BooleanSupplier condition, double debounceTime, DebounceType type) {
        m_condition = requireNonNullParam(condition, "condition", "WaitUntilCommand");
        this.debounceTime = debounceTime;
        this.type = type;
    }

    @Override
    public void initialize() {
        super.initialize();
        debouncer = new Debouncer(debounceTime, type);
    }

    /**
     * Creates a new WaitUntilCommand that ends after a given match time.
     *
     * <p>
     * NOTE: The match timer used for this command is UNOFFICIAL. Using this command
     * does NOT
     * guarantee that the time at which the action is performed will be judged to be
     * legal by the
     * referees. When in doubt, add a safety factor or time the action manually.
     *
     * @param time
     *            the match time after which to end, in seconds
     */

    @Override
    public boolean isFinished() {
        return debouncer.calculate(m_condition.getAsBoolean());
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
