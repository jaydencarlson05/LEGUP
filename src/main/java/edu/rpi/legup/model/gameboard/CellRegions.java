package edu.rpi.legup.model.gameboard;

import edu.rpi.legup.utility.DisjointSets;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * CellRegions a collection of cells in a grid within certain non-overlapping regions
 * It holds a DisjointSets object that ensures cells are not contained in multiple regions
 * It holds a data type T that must be a GridCell or a subtype of GridCell
 *
 * @param <T> Cell type to hold in regions
 */
public class CellRegions<T> {

    private DisjointSets<T> sets;

    public CellRegions()
    {
        sets = new DisjointSets<T>();
    }

    /**
     * Creates a unique set that contains the specified puzzleElement. If the specified
     * puzzleElement is null or another set already contains that puzzleElement, this method returns
     * false, indicating that a set was not created
     *
     * @param u puzzleElement to create the set from
     * @return true if the set was created, false otherwise
     */
    public boolean createSet(T u) {
        return sets.createSet(u);
    }

    /**
     * Finds and returns the representative set puzzleElement of the set that the specified
     * puzzleElement contains
     *
     * @param p puzzleElement of the set of which to find
     * @return representative set puzzleElement or null if the specified puzzleElement is null or is
     *     not in the DisjointSets
     */
    public T find(T p) {
        return sets.find(p);
    }

    /**
     * Unions two sets together. If the set are non-null and disjoint, then it returns true, false
     * otherwise
     *
     * @param p set one
     * @param q set two
     * @return returns true if sets are non-null and disjoint, false otherwise
     */
    public boolean union(T p, T q) {
        return sets.union(p, q);
    }

    /**
     * Unions two elements together, if either puzzleElement is not already in the DisjointSets, it
     * creates a set for the puzzleElement then unions the sets together. If either puzzleElement is
     * null, no action is taken.
     *
     * @param p puzzleElement one
     * @param q puzzleElement two
     */
    public void addAndUnion(T p, T q) {
        sets.addAndUnion(p, q);
    }


    /**
     * Adds a new region that contains a given set of cells. If any cell in the set is already
     * in a region, it is removed from its original region and added to the new one.
     * @param set
     */
    public void addSet(Collection<T> set)
    {
        Iterator<T> itr = set.iterator();
        if (itr.hasNext())
        {
            T representative = itr.next();
            createSet(representative);
            while (itr.hasNext())
            {
                addAndUnion(representative, itr.next());
            }
        }
    }

    /**
     * Determines whether the specified puzzleElement is in the DisjointSets
     *
     * @param u puzzleElement to check
     * @return true if the DisjointSets contains the specified puzzleElement, false otherwise
     */
    public boolean contains(T u) {
        return sets.contains(u);
    }

    /**
     * Gets the set of elements that the specified puzzleElement is contained in, or null if no such
     * set exists.
     *
     * @param p puzzleElement to get the set of
     * @return the set of elements that the specified puzzleElement if contained in, or null if no
     *     such set exists
     */
    public Set<T> getRegion(T p) {
        return sets.getSet(p);
    }

    /**
     * Gets a list of all regions
     *
     * @return list of the regions
     */
    public List<Set<T>> getAllRegions() {
        return sets.getAllSets();
    }

    /**
     * Gets the number of regions
     *
     * @return the number of regions
     */
    public int regionCount() {
        return sets.setCount();
    }

    /**
     * Gets the total number of elements among all regions
     *
     * @return the number of elements in all regions
     */
    public int size() {
        return sets.size();
    }

}
