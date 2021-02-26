package pl.moderr.moderrkowo.core.utils;

import org.jetbrains.annotations.Contract;

public class RandomMinMax{
    private int min;
    private int max;

    @Contract(pure = true)
    public RandomMinMax(int min, int max){
        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    public int getRandom(){
        return RandomUtils.getRandomInt(min, max);
    }
}
