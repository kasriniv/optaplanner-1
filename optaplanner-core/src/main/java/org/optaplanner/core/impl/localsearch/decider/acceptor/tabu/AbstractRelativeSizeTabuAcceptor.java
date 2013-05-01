package org.optaplanner.core.impl.localsearch.decider.acceptor.tabu;

import org.optaplanner.core.impl.localsearch.scope.LocalSearchStepScope;


public abstract class AbstractRelativeSizeTabuAcceptor extends AbstractTabuAcceptor {

    protected double tabuRatio = -1;
    protected double fadingTabuRatio = 0;

    public void setTabuSizeToEntityCountRatio(double ratio) {
        this.tabuRatio = ratio;
    }

    public void setFadingTabuSizeToEntityCountRatio(double ratio) {
        this.fadingTabuRatio = ratio;
    }
    
    protected void validate() {
        if (tabuRatio < 0 || tabuRatio > 1) {
            throw new IllegalArgumentException("The tabuRatio (" + tabuRatio
                    + ") must fall into <0, 1> range.");
        }
        if (fadingTabuRatio < 0 || fadingTabuRatio > 1) {
            throw new IllegalArgumentException("The fadingTabuRatio (" + fadingTabuRatio
                    + ") must fall into <0, 1> range.");
        }
        if (tabuRatio + fadingTabuRatio == 0) {
            throw new IllegalArgumentException("The sum of tabuSize and fadingTabuSize should be at least 1.");
        }
    }

    @Override
    protected int calculateActualMaximumSize(LocalSearchStepScope scope) {
        return this.calculateFadingTabuSize(scope) + this.calculateRegularTabuSize(scope);
    }
    
    @Override
    protected int calculateFadingTabuSize(LocalSearchStepScope scope) {
        return (int)Math.round(scope.getScoreDirector().getWorkingPlanningEntityList().size() * this.fadingTabuRatio);
    }

    @Override
    protected int calculateRegularTabuSize(LocalSearchStepScope scope) {
        return (int)Math.round(scope.getScoreDirector().getWorkingPlanningEntityList().size() * this.tabuRatio);
    }

}