/**
 * Class: Pair
 * @author edghklj
 *
 * @param <L> type of left
 * @param <R> type of right
 */

package iplom;

public class Pair<L,R> {

  /**
   * Members: L left and R right
   */
  private L left = null;
  private R right = null;

  /**
   * Constructors
   */
  public Pair() {}
  
  public Pair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Get the members
   * @return
   */
  public L getLeft() { return left; }
  public R getRight() { return right; }
  
  /**
   * Set the members
   */
  public void setLeft(L left) { this.left = left; }
  public void setRight(R right) { this.right = right; }

  /**
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

  /**
   * check whether it si equal to another Object
   * Based on its type and two members
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair<?, ?> pairo = (Pair<?, ?>) o;
    return this.left.equals(pairo.getLeft()) &&
           this.right.equals(pairo.getRight());
  }

}