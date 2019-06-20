package pl.jgardo.field.bool;

class StateHolderNonFinal {
    private boolean featureOn;

    StateHolderNonFinal() {

    }

    void setFeatureOn(boolean featureOn) {
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

