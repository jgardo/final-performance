package pl.jgardo.interfaces;

public interface DefaultInterface {
    default int someMethodInvocation() {
        return 5;
    }
}
