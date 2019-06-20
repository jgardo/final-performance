package pl.jgardo.methods;

public final class SubClass extends Superclass {

    public final int inheritedToMarkAsFinal() {
        return 5;
    }

    public final int finalInSub() {
        return 5;
    }

    public int nonFinal() {
        return 5;
    }
}
