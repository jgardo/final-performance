package pl.jgardo.field.bool;

class StateHolderFinal {
    private final boolean featureOn;

    StateHolderFinal(boolean featureOn) {
        this.featureOn = featureOn;
    }

    int doSth() {
        int count = 0;
        if (featureOn) {
            count++;
        }

        if (featureOn) {
            count++;
        }

        return count;
    }
}
