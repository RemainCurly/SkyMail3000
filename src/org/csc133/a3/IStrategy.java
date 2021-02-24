package org.csc133.a3;

public interface IStrategy
{
    /**
     * Changes the strategy of a NonPlayerHelicopter
     * @param gw An instance of the active GameWorld
     * @param nph An instance of the NPH we're executing the strategy of
     */
    public void executeStrategy(GameWorld gw, NonPlayerHelicopter nph);
}
