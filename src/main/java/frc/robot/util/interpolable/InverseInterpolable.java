package frc.robot.util.interpolable;

// From Team 254
// https://github.com/Team254/FRC-2019-Public/blob/f99f09a15a2c8e7b5df868ec3a4a81105dc88112/src/main/java/com/team254/lib/util/InverseInterpolable.java

/**
 * InverseInterpolable is an interface used by an Interpolating Tree as the Key type. Given two
 * endpoint keys and a third query key, an InverseInterpolable object can calculate the
 * interpolation parameter of the query key on the interval [0, 1].
 *
 * @param <T> The Type of InverseInterpolable
 * @see InterpolatingTreeMap
 */
public interface InverseInterpolable<T> {
  /**
   * Given this point (lower), a query point (query), and an upper point (upper), estimate how far
   * (on [0, 1]) between 'lower' and 'upper' the query point lies.
   *
   * @return The interpolation parameter on [0, 1] representing how far between this point and the
   *     upper point the query point lies.
   */
  double inverseInterpolate(T upper, T query);
}
